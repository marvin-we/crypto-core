package eu.bittrade.crypto.core;

public class Sha256ChecksumProvider implements Base58ChecksumProvider {
    @Override
    public byte[] calculateActualChecksum(byte[] data) {
        return Sha256Hash.hashTwice(data);
    }
}
