package com.ecwid.apiclient.v3.metric

import java.io.IOException
import java.io.InputStream
import java.util.concurrent.atomic.AtomicLong

internal class CountingInputStream(
	private val peer: InputStream,
	private val counter: AtomicLong
) : InputStream() {
    @Throws(IOException::class)
    override fun read(): Int {
        val readByte = peer.read()
        if (readByte != -1) {
            counter.incrementAndGet()
        }
        return readByte
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray): Int {
        val readBytes = peer.read(b)
        if (readBytes != -1) {
            counter.addAndGet(readBytes.toLong())
        }
        return readBytes
    }

    @Throws(IOException::class)
    override fun read(b: ByteArray, off: Int, len: Int): Int {
        val readBytes = peer.read(b, off, len)
        if (readBytes != -1) {
            counter.addAndGet(readBytes.toLong())
        }
        return readBytes
    }

    @Throws(IOException::class)
    override fun skip(n: Long): Long {
        return peer.skip(n)
    }

    @Throws(IOException::class)
    override fun available(): Int {
        return peer.available()
    }

    @Throws(IOException::class)
    override fun close() {
        peer.close()
    }

    override fun mark(readLimit: Int) {
        peer.mark(readLimit)
    }

    @Throws(IOException::class)
    override fun reset() {
        peer.reset()
    }

    override fun markSupported(): Boolean {
        return peer.markSupported()
    }
}
