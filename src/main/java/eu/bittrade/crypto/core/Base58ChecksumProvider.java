package eu.bittrade.crypto.core;

public interface Base58ChecksumProvider {
    public byte[] calculateActualChecksum(byte[] data);
}
