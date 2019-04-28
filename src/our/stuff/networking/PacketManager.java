package our.stuff.networking;

import com.brackeen.javagamebook.tilegame.sprites.Creature;

public class PacketManager
{
	
	public static final byte TYPE_PING = 0;
	public static final byte TYPE_CONNECT = 1;
	public static final byte TYPE_ACCEPT = 2;
	public static final byte TYPE_CHAT = 3;
	public static final byte TYPE_DISCONNECT = 4;
	public static final byte TYPE_START = 5;
	public static final byte TYPE_SPAWN = 6;
	public static final byte TYPE_PLAYERPOS = 7;
	
	public static byte[] genPacketData(byte packetType)
	{
		return genPacketData(packetType, new byte[64]);
	}
	
	public static byte[] genPacketData(byte packetType, byte[] data)
	{
		byte[] finalData = new byte[data.length + 1];
		finalData[0] = packetType;
		for (int i = 0; i < data.length; i++)
		{
			finalData[i + 1] = data[i];
		}
		return finalData;
	}
	
	public static byte[] genChatPacket(String message)
	{
		byte[] txtBytes = message.getBytes();
		return genPacketData(TYPE_CHAT, txtBytes);
	}
	
	public static byte[] genEnemySpawnPacket(Creature enemy)
	{
		String name = enemy.getClass().getSimpleName();
		byte[] enemyBytes = name.getBytes();
		return genPacketData(TYPE_SPAWN, enemyBytes);
	}
	
}
