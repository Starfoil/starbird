package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class Friend {
	private int xpos;
	private int ypos;
	private Skin skin;
	private int maxHealth;
	private int health;
	private int maxMana;
	private int mana;
	private ArrayList<Bullet> bullets;
	private int cooldown = 0;
	private Rectangle hitbox;
	
	public abstract void draw(Graphics g, Image[] playerImages, Image[] bulletImages);
	public abstract void move();
	public abstract void shoot();
	public abstract void update();
	public abstract void updateHealth(double rawDamage);
	public abstract void updateMana();
	public abstract void updateBullets();
}
