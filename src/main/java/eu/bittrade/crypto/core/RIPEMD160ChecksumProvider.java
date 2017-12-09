package eu.bittrade.crypto.core;

import org.spongycastle.crypto.digests.RIPEMD160Digest;

public class RIPEMD160ChecksumProvider implements Base58ChecksumProvider {
    @Override
    public byte[] calculateActualChecksum(byte[] data) {
        RIPEMD160Digest ripemd160Digest = new RIPEMD160Digest();
        ripemd160Digest.update(data, 0, data.length);
        byte[] actualChecksum = new byte[ripemd160Digest.getDigestSize()];
        ripemd160Digest.doFinal(actualChecksum, 0);
        return actualChecksum;
    }
}
