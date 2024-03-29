/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:06 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 11:46 PM
 */

package chat.revolt.domain

object UlidTimeDecoder {

    private val charToByteMapping = byteArrayOf(
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0x00.toByte(), 0x01.toByte(), 0x02.toByte(), 0x03.toByte(), 0x04.toByte(), 0x05.toByte(),
        0x06.toByte(), 0x07.toByte(), 0x08.toByte(), 0x09.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0x0a.toByte(),
        0x0b.toByte(), 0x0c.toByte(), 0x0d.toByte(), 0x0e.toByte(), 0x0f.toByte(), 0x10.toByte(),
        0x11.toByte(), 0xff.toByte(), 0x12.toByte(), 0x13.toByte(), 0xff.toByte(), 0x14.toByte(),
        0x15.toByte(), 0xff.toByte(), 0x16.toByte(), 0x17.toByte(), 0x18.toByte(), 0x19.toByte(),
        0x1a.toByte(), 0xff.toByte(), 0x1b.toByte(), 0x1c.toByte(), 0x1d.toByte(), 0x1e.toByte(),
        0x1f.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0x0a.toByte(), 0x0b.toByte(), 0x0c.toByte(), 0x0d.toByte(), 0x0e.toByte(),
        0x0f.toByte(), 0x10.toByte(), 0x11.toByte(), 0xff.toByte(), 0x12.toByte(), 0x13.toByte(),
        0xff.toByte(), 0x14.toByte(), 0x15.toByte(), 0xff.toByte(), 0x16.toByte(), 0x17.toByte(),
        0x18.toByte(), 0x19.toByte(), 0x1a.toByte(), 0xff.toByte(), 0x1b.toByte(), 0x1c.toByte(),
        0x1d.toByte(), 0x1e.toByte(), 0x1f.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte(),
        0xff.toByte(), 0xff.toByte(), 0xff.toByte(), 0xff.toByte()
    )

    fun getTimestamp(ulid: CharSequence): Long {
        if(ulid.isBlank()) return 0
        return (charToByteMapping[ulid[0].code].toLong() shl 45
                or (charToByteMapping[ulid[1].code].toLong() shl 40)
                or (charToByteMapping[ulid[2].code].toLong() shl 35)
                or (charToByteMapping[ulid[3].code].toLong() shl 30)
                or (charToByteMapping[ulid[4].code].toLong() shl 25)
                or (charToByteMapping[ulid[5].code].toLong() shl 20)
                or (charToByteMapping[ulid[6].code].toLong() shl 15)
                or (charToByteMapping[ulid[7].code].toLong() shl 10)
                or (charToByteMapping[ulid[8].code].toLong() shl 5)
                or charToByteMapping[ulid[9].code].toLong())
    }
}