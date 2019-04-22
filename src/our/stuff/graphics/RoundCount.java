package our.stuff.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import com.brackeen.javagamebook.sound.Sound;
import com.brackeen.javagamebook.sound.SoundManager;
import com.brackeen.javagamebook.tilegame.GameManager;

/**
 * The round tally counter
 * @author Kyan
 *
 */
public class RoundCount implements ActionListener
{
	private ArrayList<BufferedImage> tallies = new ArrayList<BufferedImage>();
	private int round = 0;
	
	private boolean changing = false;
	private boolean fading = false;
	private boolean held = false;
	
	private Timer totalClock = new Timer(21000, this);
	private Timer fadeClock = new Timer(9600, this);
	private Sound sound;
	private SoundManager sm;
	private long elapsedTime;
	private long totalTime;
	private long fadeTime;
	
	private float f = 0.0f;
	
	public RoundCount(Sound sound, SoundManager sm)
	{
		this.sound = sound;
		this.sm = sm;
	}
	
	public void increment()
	{
		if (!changing)
		{
			changing = true;
			fading = true;
			fadeClock.start();
			totalClock.restart();
			round++;
			String name = "tally" + (1 + (round-1)%5) + ".png";
			Image s = GameManager.getGameManagerInstance().getResourceManagerInstance().loadImage(name);
			BufferedImage bi = new BufferedImage(s.getWidth(null), s.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			tallies.add(bi);
			Graphics2D gr = bi.createGraphics();
			gr.drawImage(s, 0, 0, null);
			gr.dispose();
			sm.play(sound);
		}
	}
	
	public void update(long time)
	{
		if(changing)
		{
			elapsedTime = time;
			totalTime += elapsedTime;
			if(!fading)
			{
				fadeTime += elapsedTime;
			}
			if (fadeTime > 2300)
			{
				fadeTime = 2300;
			}
		}
	}
	
	public void draw(Graphics g)
	{
		for(int i = 0; i < tallies.size(); i++)
		{
			int offset = 0;
			if ((i+1) % 5 == 0)
			{
				offset = -44;
			}
			Graphics2D gr = (Graphics2D)g;
			if(changing)
			{
				if(!held)
				{
					f = (float)Math.sin(2f * Math.PI * 0.0007f * totalTime);
					f = (f+1f)/2f;
					f*= 3;
				}
				if (!fading)
				{
					f = (float)fadeTime/2300f;
				}
				if (f <= 0.1f && totalTime > 6000 && fading)
				{
					held = true;
					f = 0.0f;
				}
				float[] scales = { f, 1.0f, 1.0f, f };
				BufferedImageOp op = new RescaleOp(scales, new float[4], null);
				if (i < tallies.size()-1 || !fading)
					gr.drawImage(tallies.get(i), op, 20 + 11*i + offset, 530);
			}
			else
			{
				gr.drawImage(tallies.get(i), 20 + 11*i + offset, 530, null);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		if (arg0.getSource() == totalClock)
		{
		changing = false;
		held = false;
		totalTime = 0;
		fadeTime = 0;
		}
		if (arg0.getSource() == fadeClock)
		{
			fading = false;
			fadeClock.stop();
		}
	}
}
