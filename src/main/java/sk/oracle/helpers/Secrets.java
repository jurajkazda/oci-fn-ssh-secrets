package sk.oracle.helpers;

import sk.oracle.secrets.fn.SecretsFunction;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public class Secrets {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        SecretsFunction fn = new SecretsFunction();

        KeyPair keyPair = fn.generateKeyPair();
        String privateKey = new String(fn.convertPrivateKeyToPem(keyPair.getPrivate()));
        System.out.println(privateKey);

        String pubKey = fn.convertPublicKeyToOpenSSH(keyPair.getPublic());
        System.out.println(pubKey);
    }
}
