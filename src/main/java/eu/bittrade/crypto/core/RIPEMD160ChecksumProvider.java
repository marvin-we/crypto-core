package eu.bittrade.crypto.core;

import org.spongycastle.crypto.digests.RIPEMD160Digest;

/**
 * Calculate the actual checksum of a <code>Base58</code> value for blockchains
 * based on <code>RIPEMD160</code>.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
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
