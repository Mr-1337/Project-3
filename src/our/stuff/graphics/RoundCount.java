package our.stuff.graphics;

import java.awt.Color;
import java.awt.Font;
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
	private boolean newRound = false;
	
	private Timer totalClock = new Timer(21000, this);
	private Timer fadeClock = new Timer(9600, this);
	private Sound sound;
	private SoundManager sm;
	private long elapsedTime;
	private long totalTime;
	private long fadeTime;
	
	private Font font = new Font("Impact", Font.BOLD, 60);
	
	private float f = 0.0f;
	
	public RoundCount(Sound sound, SoundManager sm)
	{
		this.sound = sound;
		this.sm = sm;
		for (int i = 0; i < round; i++)
		{
			String name = "tally" + (1 + (i)%5) + ".png";
			Image s = GameManager.getGameManagerInstance().getResourceManagerInstance().loadImage(name);
			BufferedImage bi = new BufferedImage(s.getWidth(null), s.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			tallies.add(bi);
			Graphics2D gr = bi.createGraphics();
			gr.drawImage(s, 0, 0, null);
			gr.dispose();
		}
	}
	
	public int getRound()
	{
		return round;
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
	
	public boolean isNewRound()
	{
		boolean temp = newRound;
		newRound = false;
		return temp;
	}
	
	public void draw(Graphics g)
	{
		if (round < 21)
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
		else if(round == 21)
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
						f = (float)Math.min(fadeTime/2300f, 1.0f);
					}
					if (f <= 0.1f && totalTime > 6000 && fading)
					{
						held = true;
						f = 0.0f;
					}
					float[] scales = { f, 1.0f, 1.0f, f };
					BufferedImageOp op = new RescaleOp(scales, new float[4], null);
					if (fading)
					{
						if (i < tallies.size()-1)
							gr.drawImage(tallies.get(i), op, 20 + 11*i + offset, 530);
					}
				}
			}
			if (!fading && changing)
			{
				g.setFont(font);
				g.setColor(new Color(172f/255, 22f/255, 22f/255, f));
				g.drawString(Integer.toString(round), 20, 570);
			}
			if (!changing)
			{
				g.setFont(font);
				g.setColor(new Color(172f/255, 22f/255, 22f/255, 1.0f));
				g.drawString(Integer.toString(round), 20, 570);
			}
		}
		else
		{
			if(changing)
			{
				String text;
				if(!held)
				{
					f = (float)Math.sin(2f * Math.PI * 0.0007f * totalTime);
					f = (f+1f)/2f;
				}
				if (!fading)
				{
					f = (float)Math.min(fadeTime/2300f, 1.0f);
				}
				if (f <= 0.1f && totalTime > 6000 && fading)
				{
					held = true;
					f = 0.01f;
				}
				g.setFont(font);
				g.setColor(new Color(172f/255, 22f/255, 22f/255, f));
				
				if (!fading)
					text = String.valueOf(round);
				else
					text = String.valueOf(round-1);
				g.drawString(text, 20, 570);
			}
			else
			{
				g.setFont(font);
				g.setColor(new Color(172f/255, 22f/255, 22f/255, 1.0f));
				g.drawString(Integer.toString(round), 20, 570);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		// TODO Auto-generated method stub
		if (arg0.getSource() == totalClock)
		{
			if (changing)
			{
				newRound = true;
			}
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
