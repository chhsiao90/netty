/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.ssl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import javax.net.ssl.SSLEngine;
import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.ssl.OpenSslTestUtils.checkShouldUseKeyManagerFactory;
import static io.netty.internal.tcnative.SSL.SSL_CVERIFY_IGNORED;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class JdkOpenSslEngineInteroptTest extends SSLEngineTest {

    public JdkOpenSslEngineInteroptTest() {
        super(SslProvider.isTlsv13Supported(SslProvider.JDK) &&
                SslProvider.isTlsv13Supported(SslProvider.OPENSSL));
    }

    @Override
    protected List<SSLEngineTestParam> newTestParams() {
        List<SSLEngineTestParam> params = super.newTestParams();
        List<SSLEngineTestParam> testParams = new ArrayList<SSLEngineTestParam>();
        for (SSLEngineTestParam param: params) {
            testParams.add(new OpenSslEngineTestParam(true, param));
            testParams.add(new OpenSslEngineTestParam(false, param));
        }
        return testParams;
    }

    @BeforeAll
    public static void checkOpenSsl() {
        OpenSsl.ensureAvailability();
    }

    @Override
    protected SslProvider sslClientProvider() {
        return SslProvider.JDK;
    }

    @Override
    protected SslProvider sslServerProvider() {
        return SslProvider.OPENSSL;
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthSameCerts(SSLEngineTestParam param) throws Throwable {
        super.testMutualAuthSameCerts(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthDiffCerts(SSLEngineTestParam param) throws Exception {
        super.testMutualAuthDiffCerts(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthDiffCertsServerFailure(SSLEngineTestParam param) throws Exception {
        super.testMutualAuthDiffCertsServerFailure(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthDiffCertsClientFailure(SSLEngineTestParam param) throws Exception {
        super.testMutualAuthDiffCertsClientFailure(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthInvalidIntermediateCASucceedWithOptionalClientAuth(SSLEngineTestParam param)
            throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testMutualAuthInvalidIntermediateCASucceedWithOptionalClientAuth(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthInvalidIntermediateCAFailWithOptionalClientAuth(SSLEngineTestParam param)
            throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testMutualAuthInvalidIntermediateCAFailWithOptionalClientAuth(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthInvalidIntermediateCAFailWithRequiredClientAuth(SSLEngineTestParam param)
            throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testMutualAuthInvalidIntermediateCAFailWithRequiredClientAuth(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthValidClientCertChainTooLongFailOptionalClientAuth(SSLEngineTestParam param)
            throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testMutualAuthValidClientCertChainTooLongFailOptionalClientAuth(param);
    }

    @Override
    @DisabledOnOs(value = OS.WINDOWS, disabledReason = "Disable until figured out why this sometimes fail on the CI")
    public void testMutualAuthValidClientCertChainTooLongFailRequireClientAuth(SSLEngineTestParam param)
            throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testMutualAuthValidClientCertChainTooLongFailRequireClientAuth(param);
    }

    @Override
    public void testSessionAfterHandshakeKeyManagerFactoryMutualAuth(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testSessionAfterHandshakeKeyManagerFactoryMutualAuth(param);
    }

    @Override
    public void testSessionAfterHandshakeKeyManagerFactory(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testSessionAfterHandshakeKeyManagerFactory(param);
    }

    @Override
    protected void mySetupMutualAuthServerInitSslHandler(SslHandler handler) {
        ReferenceCountedOpenSslEngine engine = (ReferenceCountedOpenSslEngine) handler.engine();
        engine.setVerify(SSL_CVERIFY_IGNORED, 1);
    }

    @Override
    protected boolean mySetupMutualAuthServerIsValidClientException(Throwable cause) {
        // TODO(scott): work around for a JDK issue. The exception should be SSLHandshakeException.
        return super.mySetupMutualAuthServerIsValidClientException(cause) || causedBySSLException(cause);
    }

    @Override
    public void testHandshakeSession(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testHandshakeSession(param);
    }

    @Override
    public void testSupportedSignatureAlgorithms(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testSupportedSignatureAlgorithms(param);
    }

    @Override
    public void testSessionLocalWhenNonMutualWithKeyManager(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testSessionLocalWhenNonMutualWithKeyManager(param);
    }

    @Override
    public void testSessionLocalWhenNonMutualWithoutKeyManager(SSLEngineTestParam param) throws Exception {
        // This only really works when the KeyManagerFactory is supported as otherwise we not really know when
        // we need to provide a cert.
        assumeTrue(OpenSsl.supportsKeyManagerFactory());
        super.testSessionLocalWhenNonMutualWithoutKeyManager(param);
    }

    @Override
    public void testSessionCache(SSLEngineTestParam param) throws Exception {
        assumeTrue(OpenSsl.isSessionCacheSupported());
        super.testSessionCache(param);
    }

    @Override
    public void testSessionCacheTimeout(SSLEngineTestParam param) throws Exception {
        assumeTrue(OpenSsl.isSessionCacheSupported());
        super.testSessionCacheTimeout(param);
    }

    @Override
    public void testSessionCacheSize(SSLEngineTestParam param) throws Exception {
        assumeTrue(OpenSsl.isSessionCacheSupported());
        super.testSessionCacheSize(param);
    }

    @MethodSource("newTestParams")
    @ParameterizedTest
    @Override
    public void testRSASSAPSS(SSLEngineTestParam param) throws Exception {
        checkShouldUseKeyManagerFactory();
        super.testRSASSAPSS(param);
    }

    @Override
    protected SSLEngine wrapEngine(SSLEngine engine) {
        return Java8SslTestUtils.wrapSSLEngineForTesting(engine);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected SslContext wrapContext(SSLEngineTestParam param, SslContext context) {
        if (context instanceof OpenSslContext && param instanceof OpenSslEngineTestParam) {
            ((OpenSslContext) context).setUseTasks(((OpenSslEngineTestParam) param).useTasks);
            // Explicit enable the session cache as its disabled by default on the client side.
            ((OpenSslContext) context).sessionContext().setSessionCacheEnabled(true);
        }
        return context;
    }
}
