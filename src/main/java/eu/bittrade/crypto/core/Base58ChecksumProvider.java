package eu.bittrade.crypto.core;

/**
 * This interface allows to implement a custom method which will be called to
 * receive the actual checksum of a <code>Base58</code> value.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
public interface Base58ChecksumProvider {
    /**
     * Calculate the actual checksum of the given <code>data</code>.
     * 
     * @param data
     *            the <code>Base58</code> to extract the checksum from
     * @return the actual checksum of the <code>data</code>
     */
    public byte[] calculateActualChecksum(byte[] data);
}
