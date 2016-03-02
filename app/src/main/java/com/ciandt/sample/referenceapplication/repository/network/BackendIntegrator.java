package com.ciandt.sample.referenceapplication.repository.network;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.ciandt.sample.referenceapplication.infrastructure.OperationResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class BackendIntegrator {
    private static final List<String> certs = Collections.singletonList("my_backend_certificates");

    private Context mContext;

    public BackendIntegrator(Context context) {
        mContext = context.getApplicationContext();
    }

    public OperationResult<JSONObject> execute(final ConnectionParameters connectionParameters) {
        OperationResult<JSONObject> result = new OperationResult<>();
        OutputStreamWriter writer = null;
        InputStream inputStream = null;
        Scanner scanner = null;

        try {
            Uri.Builder uriBuilder = new Uri.Builder()
                    .scheme(connectionParameters.getScheme())
                    .authority(connectionParameters.getHost())
                    .path(connectionParameters.getEndpoint());

            AbstractMap<String, String> urlParameters = connectionParameters.getUrlParameters();
            if (urlParameters != null) {
                for (Map.Entry<String, String> urlParam : urlParameters.entrySet()) {
                    uriBuilder.appendQueryParameter(urlParam.getKey(), urlParam.getValue());
                }
            }

            URL url = new URL(URLDecoder.decode(uriBuilder.build().toString(), "UTF-8"));

            if (NetworkUtils.isConnectionAvailable(mContext)) {
                String operationMethod = connectionParameters.getOperationMethod();

                // Creating the operation connection and setting parameters
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(operationMethod);
                connection.setUseCaches(false);
                connection.setDoInput(true);
                if (connectionParameters.isSecurityEnabled()) {
                    // ConnectionParameters builder ensures that, if security is enabled, scheme is https
                    ((HttpsURLConnection) connection).setSSLSocketFactory(getSSLContext().getSocketFactory());
                    ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                            return hv.verify(connectionParameters.getHost(), session);
                        }
                    });
                }

                connection.setConnectTimeout(connectionParameters.getConnectionTimeout());

                // TODO: These properties are only for demonstration purposes. Remove them in your project
                connection.setRequestProperty("X-Parse-Application-Id", "oUZjvMyLohNCAlAmoi8rWQdUq1MXyDNxHvjTwVUM");
                connection.setRequestProperty("X-Parse-REST-API-Key", "lr9YZR4n4eJ0DGTlp46rxKgWdlF2V4k3L2djCIoL");

                if (operationMethod.equals(ConnectionParameters.OperationMethod.POST)
                        || operationMethod.equals(ConnectionParameters.OperationMethod.PUT)) {
                    // This can be parametrized
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    writer = new OutputStreamWriter(connection.getOutputStream());
                    String data = connectionParameters.getRequestBody();
                    Log.d("ReferenceApplication", "Sending data: " + data);
                    writer.write(data);
                    writer.close();
                }

                // Validating the response
                int httpResponseCode = connection.getResponseCode();

                if (httpResponseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = new BufferedInputStream(connection.getInputStream());
                } else {
                    inputStream = new BufferedInputStream(connection.getErrorStream());
                    result.setError(httpResponseCode);
                }
                scanner = new Scanner(inputStream);
                scanner.useDelimiter("\\A");

                String responseData = "";
                if (scanner.hasNext()) {
                    responseData = scanner.next();
                }

                Log.d("ReferenceApplication", "Server Response:");
                Log.d("ReferenceApplication", responseData);

                // Validating the result
                JSONObject resultData;
                if (!TextUtils.isEmpty(responseData)) {
                    resultData = new JSONObject(responseData);
                } else {
                    resultData = new JSONObject();
                }

                result.setResult(resultData);

            } else {
                Log.e("ReferenceApplication", "No connection available. Aborting...");
                result.setError(NetworkConstants.Error.NO_CONNECTION_AVAILABLE);
                result.setResult(null);
            }
        } catch (SocketTimeoutException timeoutException) {
            Log.e("ReferenceApplication", "Connection error. ", timeoutException);
            result.setError(NetworkConstants.Error.CONNECTION_TIMEOUT_ERROR);
        } catch (ConnectException connectionException) {
            Log.e("ReferenceApplication", "Connection error. ", connectionException);
            result.setError(NetworkConstants.Error.NO_CONNECTION_AVAILABLE);
        } catch (IOException ioException) {
            Log.e("ReferenceApplication", "Connection error. ", ioException);
            result.setError(NetworkConstants.Error.GENERIC_CONNECTION_ERROR);
        } catch (JSONException jsonException) {
            Log.e("ReferenceApplication", "Connection error. ", jsonException);
            result.setError(NetworkConstants.Error.GENERIC_CONNECTION_ERROR);
        } catch (Exception e) {
            Log.e("ReferenceApplication", "Connection error. ", e);
            result.setError(NetworkConstants.Error.GENERIC_CONNECTION_ERROR);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (scanner != null) {
                    scanner.close();
                }
            } catch (IOException e) {
                Log.e("ReferenceApplication", e.toString());
            }
        }

        return result;
    }

    private static SSLContext getSSLContext()
            throws CertificateException,
            IOException,
            KeyStoreException,
            NoSuchAlgorithmException,
            KeyManagementException {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);

        for (int i = 0; i < certs.size(); i++) {
            InputStream inputStream = new ByteArrayInputStream(Base64.decode(
                    certs.get(i).getBytes(), Base64.DEFAULT));
            Certificate ca;

            try {
                ca = cf.generateCertificate(inputStream);
            } finally {
                inputStream.close();
            }
            String alias = "ca" + i;
            keyStore.setCertificateEntry(alias, ca);
        }

        // Load keystore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);

        return sslContext;
    }
}
