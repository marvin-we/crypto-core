<center>
  
![CryptoCoreLogo](https://imgur.com/Lujy7CJ.png)

</center>

This project has the ambitious target to provide a *Swiss Army knife* for Blockchain related projects. 

# Background
It is well known that every Blockchain technology should have its own special features which makes it more or less unique. Because of this, every specific Blockchain requires its own specific library which implements and supports those features. When having a look at *Java Api implementations*, [bitcoinj](https://github.com/bitcoinj/bitcoinj), [etheriumj](https://github.com/ethereum/ethereumj) or [steemj](https://github.com/marvin-we/steem-java-api-wrapper) are good examples of those unique libraries.

However, a lot of Blockchain technologies use the same core mechanisms which every Blockchain related project has to solve. To make this more clear, here are some examples:
* SHA256 hashing
* Base58 encoding/decoding
* HEX encoding/decoding
* Handling of private and public keys
* Signing of messages
* Handling of common C types like `VarInt` that do not exist in Java 
* ...

The team around [bitcoinj](https://github.com/bitcoinj/bitcoinj), which is the oldest and most proven *Java Api implementation* for a Blockchain, already solved all those issue. Sadly, the implementations are specific for Bitcoin and a big part can't be used out of the box for other Blockchain technologies. This fact forced other projects to:

1. Implement a custom solution (e.g. the IOTA Java Api).
2. Copy the source code of [bitcoinj](https://github.com/bitcoinj/bitcoinj) into their own project and adjust it (e.g. etheriumj).
3. Use the full [bitcoinj](https://github.com/bitcoinj/bitcoinj) project and build the required changes around it (e.g. steemj).

The first approach is something you should only take into concideration if you have a really strong crypto and blockchain background as the those topics require a lot of knowledge. 

The second approach is a better idea, as the well tested, documented and proven classes of the [bitcoinj](https://github.com/bitcoinj/bitcoinj) project are used. The disadvantage of this approach is that updates and fixes require a manual merge.

The third approach solves also the problem of manual merges, but requires to have add the whole [bitcoinj](https://github.com/bitcoinj/bitcoinj) project with all its dependencies and classes while only using ~2% of their code.

# How crypto core solves this problem?

The idea behind *crypto core* is to provide a flexible and well documented *Swiss Army knife* for as much Blockchain technologies as possible. This is archieved by extracting non Bitcoin related classes from the great [bitcoinj](https://github.com/bitcoinj/bitcoinj) library and making those methods more flexible.

## Example
The following snippets show the difference between *bitcoinj* and *crypto core* using the `signMessage` as an example. 

### Using BitcoinJ 
The `ECKey` object of the [bitcoinj](https://github.com/bitcoinj/bitcoinj) project is used in a lot of other Blockchain projects like steemj, etheriumj or graphenej. It provides a `signMessage` method which can be used to sign a message and, for sure, this is something every Blockchain related project has to do.

Sadly, the bitcoinj implementation is doing this by creating an SHA256 Hash of the message as you can see in line 2:

```JAVA
    public String signMessage(String message, @Nullable KeyParameter aesKey) throws KeyCrypterException {
        byte[] data = Utils.formatMessageForSigning(message);
        Sha256Hash hash = Sha256Hash.twiceOf(data);
        ECDSASignature sig = sign(hash, aesKey);
        // Now we have to work backwards to figure out the recId needed to recover the signature.
        int recId = -1;
        for (int i = 0; i < 4; i++) {
            ECKey k = ECKey.recoverFromSignature(i, sig, hash, isCompressed());
            if (k != null && k.pub.equals(pub)) {
                recId = i;
                break;
            }
        }
        if (recId == -1)
            throw new RuntimeException("Could not construct a recoverable key. This should never happen.");
        int headerByte = recId + 27 + (isCompressed() ? 4 : 0);
        byte[] sigData = new byte[65];  // 1 header + 32 bytes for R + 32 bytes for S
        sigData[0] = (byte)headerByte;
        System.arraycopy(Utils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32);
        System.arraycopy(Utils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32);
        return new String(Base64.encode(sigData), Charset.forName("UTF-8"));
    }
```

So only because of the first two lines the whole rest of the method can't be used for other blockchain projects that do not generate the hash of a message in the exact same way. This forces developers to search for other solutions instead of being able to reuse the well tested [bitcoinj](https://github.com/bitcoinj/bitcoinj) code.

### Using Crypto Core
*Crypto Core* solves this issue by removing Bitcoin related functionallities from those methods. In this concret example it allows to provide the hash itself as a parameter so that the remaining 90% of the method can be reused. 

```JAVA
    public String signMessage(Sha256Hash messageHash, @Nullable KeyParameter aesKey) {
        ECDSASignature sig = sign(messageHash, aesKey);
        // Now we have to work backwards to figure out the recId needed to
        // recover the signature.
        int recId = -1;
        for (int i = 0; i < 4; i++) {
            ECKey k = ECKey.recoverFromSignature(i, sig, messageHash, isCompressed());
            if (k != null && k.pub.equals(pub)) {
                recId = i;
                break;
            }
        }
        if (recId == -1)
            throw new RuntimeException("Could not construct a recoverable key. This should never happen.");
        int headerByte = recId + 27 + (isCompressed() ? 4 : 0);
        byte[] sigData = new byte[65]; // 1 header + 32 bytes for R + 32 bytes
                                       // for S
        sigData[0] = (byte) headerByte;
        System.arraycopy(CryptoUtils.bigIntegerToBytes(sig.r, 32), 0, sigData, 1, 32);
        System.arraycopy(CryptoUtils.bigIntegerToBytes(sig.s, 32), 0, sigData, 33, 32);
        return new String(Base64.encode(sigData), Charset.forName("UTF-8"));
    }
```

This would, e.g., allow etheriumj to use this method with a keccak hash.

# Technologies

* Java 7
* Maven 3+ - for building the project
* Google Protocol Buffers - for use with serialization and hardware communications

# Full Documentation
As *Crypto Core* only adopts the method signatures in most cases, the [bitcoinj documentation](https://bitcoinj.github.io) should still be the best way of searching for something. Beside that *Crypto Core* profits and extends the original JavaDoc developed by the [bitcoinj](https://github.com/bitcoinj/bitcoinj) team.

# Communication
If you want to contact me you can:
* Use [this thread](https://groups.google.com/forum/#!topic/bitcoinj/OgoHv6AgvmA) in the bitcoinj google group where I've announced this idea.
* contact me directly at the [Discord Java Channel](https://discord.gg/fsJjr3Q).
* create an [Issue](https://github.com/marvin-we/crypto-core/issues) here at GitHub.

# Contribution
This project is really young and requires the help from every user to improve and become more usable. So every kind of feedback is more than welcome.

# Binaries
*Crypto Core* binaries are pushed into the maven central repository and can be integrated with a bunch of build management tools like Maven.

## Maven
File: <i>pom.xml</i>
```Xml
<dependency>
    <groupId>eu.bittrade.libs</groupId>
    <artifactId>crypto-core</artifactId>
    <version>0.1.0pre1</version>
</dependency>
```

# How to build the project
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:

> mvn clean package

The resulting JAR can be found in the target directory as usual. 

# Bugs and Feedback
For bugs or feature requests please create a [GitHub Issue](https://github.com/marvin-we/steem-java-api-wrapper/issues). 

For general discussions or questions you can also:
* Post your questions in the [Discord Java Channel](https://discord.gg/fsJjr3Q)
* Reply to one of the SteemJ update posts on [Steemit.com](https://steemit.com/@dez1337)
* Contact me on [steemit.chat](https://steemit.chat/channel/dev)
