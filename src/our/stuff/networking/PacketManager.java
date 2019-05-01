package our.stuff.networking;

import java.nio.ByteBuffer;

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
	public static final byte TYPE_CREATUREPOS = 7;
	public static final byte TYPE_KILL = 8;
	
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
		StringBuilder sb = new StringBuilder(name);
		sb.append("END");
		name = sb.toString();
		int id = enemy.getID();
		byte[] enemyBytes = name.getBytes();
		ByteBuffer bb = ByteBuffer.allocate(enemyBytes.length + 4);
		bb.put(enemyBytes);
		bb.putInt(bb.capacity() - 4, id);
		
		return genPacketData(TYPE_SPAWN, bb.array());
	}
	
	public static byte[] genCreaturePosPacket(Creature creature)
	{
		int id = creature.getID();
		float x = creature.getX();
		float y = creature.getY();
		
		ByteBuffer billyBones = ByteBuffer.allocate(12);
		billyBones.putInt(id);
		billyBones.putFloat(x);
		billyBones.putFloat(y);
		
		return genPacketData(TYPE_CREATUREPOS, billyBones.array());
	}
	
	public static byte[] genKillPacket(Creature toBeKilled)
	{
		int id = toBeKilled.getID();
		ByteBuffer billyBones = ByteBuffer.allocate(4);
		billyBones.putInt(id);
		return genPacketData(TYPE_KILL, billyBones.array());
	}
	
}
