package net.adventuria.entity.player;

import java.awt.Color;
import java.awt.Graphics;

import net.adventuria.Component;
import net.adventuria.assets.AssetManager;
import net.adventuria.block.Block;
import net.adventuria.block.BlockType;
import net.adventuria.entity.EntityHuman;
import net.adventuria.entity.EntityID;
import net.adventuria.gui.PlayerHUD;
import net.adventuria.gui.inventory.Inventory;
import net.adventuria.level.chunk.Chunk;
import net.adventuria.location.Location;

public class EntityPlayer extends EntityHuman {
	public double fallingSpeed = 0.5D;
	public double movementSpeed = 0.5D;
	public double jumpingSpeed = 1.0D;
	public int jumpingHeight = 50;
	public int jumpingCount = 0;
	public int animation = 0;
	public int animationFrame = 0;
	public int animationTime = 30;
	public boolean isJumping = false, isFalling = false;

	public EntityPlayer(Location loc) {
		super(loc, EntityID.PLAYER);
		setBoundingTranslations(2, 2, -5, -2);
	}

	@Override
	public void Tick() {
		//isFalling = !isCollidingWithBlock(new Location(this.getBlockX(), this.getBlockX() + 2),  new Location(getBlockX(), getBlockY() + 2));
		
		isFalling = (!isCollidingWithBlock(new Location(getBlockX(), getBlockY() + 2), new Location(getBlockX(), getBlockY() + 2))) && (!isCollidingWithBlock(new Location(getBlockX() - 1, getBlockY() + 2), new Location(getBlockX() - 1, getBlockY() + 2)) || isCollidingWithBlock(new Location(getBlockX() - 1, getBlockY()), new Location(getBlockX() - 1, getBlockY() + 1))) && (!isCollidingWithBlock(new Location(getBlockX() + 1, getBlockY() + 2), new Location(getBlockX() + 1, getBlockY() + 2)) || isCollidingWithBlock(new Location(getBlockX() + 1, getBlockY()), new Location(getBlockX() + 1, getBlockY() + 1)));
		if ((!this.isJumping) && isFalling) {
			this.y += this.fallingSpeed;
			Component.sY += this.fallingSpeed;
			if(isCollidingWithBlockID(new Location(getBlockX(), getBlockY()), new Location(getBlockX(), getBlockY() + 2), BlockType.WATERSOURCE)) {
				fallingSpeed += fallingSpeed >= 0.8 ? 0 : 0.002;
			} else {
				fallingSpeed += fallingSpeed >= 3.5 ? 0 : 0.006;
			}
		} else if (Component.isJumping) {
			this.isJumping = true;
		}
		if (!isFalling) {
			if(fallingSpeed > 1) {
				setHealth((int) Math.round(getHealth() - (4 * fallingSpeed)));
			}
			fallingSpeed = 1;
		}
		if ((Component.isMoving) && (!Inventory.isOpen)) {
			boolean canMove = false;
			if (Component.dir == this.movementSpeed) {
				canMove = isCollidingWithBlock(new Location(getBlockX() + 1, getBlockY()), new Location(getBlockX() + 1, getBlockY() + 1));
			} else if (Component.dir == -this.movementSpeed) {
				canMove = isCollidingWithBlock(new Location(getBlockX() - 1, getBlockY()), new Location(getBlockX() - 1, getBlockY() + 1));
			}
			if (this.animationFrame >= this.animationTime) {
				if (this.animation > 1) {
					this.animation = 1;
				} else {
					this.animation += 1;
				}
				this.animationFrame = 0;
			} else {
				this.animationFrame += 1;
			}
			if (!canMove) {
				this.x += Component.dir;
				if (x >= (Component.pixel.width / 2D) + 10 && x <= (Chunk.CHUNK_WIDTH * Block.TILE_SIZE) - ((Component.pixel.width / 2D) + 30)) {
					Component.sX = x - ((Component.pixel.width / 2D) - 10);
				}else if(x <= 10) {
					
				}
			}
		} else {
			this.animation = 0;
		}
		if (this.isJumping) {
			if(isCollidingWithBlockID(new Location(getBlockX(), getBlockY()), new Location(getBlockX(), getBlockY() + 2), BlockType.WATERSOURCE)) {
				this.y -= this.jumpingSpeed;
				Component.sY -= this.jumpingSpeed;
				this.jumpingCount = 0;
			} else if (!isCollidingWithBlock(new Location(getBlockX(), getBlockY()), new Location(getBlockX(), getBlockY() - 1))) {
				if (this.jumpingCount >= this.jumpingHeight) {
					this.isJumping = false;
					this.jumpingCount = 0;
				} else {
					this.y -= this.jumpingSpeed;
					Component.sY -= this.jumpingSpeed;
	
					this.jumpingCount += 1;
				}
			} else {
				this.isJumping = false;
				this.jumpingCount = 0;
			}
		}
	}

	//TODO: Add lighting
	@Override
	public void Render(Graphics g) {
		int xCoord = (int) Math.round(x - Component.sX);
		int yCoord = (int) Math.round(y - Component.sY);
		if (Component.dir == this.movementSpeed) {
			g.drawImage(AssetManager.tileset_entity, xCoord, yCoord, (int) this.width + xCoord, (int) this.height + yCoord, EntityID.PLAYER.getTextureID()[0] * Block.TILE_SIZE + Block.TILE_SIZE * this.animation, EntityID.PLAYER.getTextureID()[1] * Block.TILE_SIZE, EntityID.PLAYER.getTextureID()[0] * Block.TILE_SIZE + Block.TILE_SIZE * this.animation + (int) this.width, EntityID.PLAYER.getTextureID()[1] * Block.TILE_SIZE + (int) this.height, null);
		} else {
			g.drawImage(AssetManager.tileset_entity, xCoord, yCoord, (int) this.width + xCoord, (int) this.height + yCoord, EntityID.PLAYER.getTextureID()[0] * Block.TILE_SIZE + Block.TILE_SIZE * this.animation + (int) this.width, EntityID.PLAYER.getTextureID()[1] * Block.TILE_SIZE, EntityID.PLAYER.getTextureID()[0] * Block.TILE_SIZE + Block.TILE_SIZE * this.animation, EntityID.PLAYER.getTextureID()[1] * Block.TILE_SIZE + (int) this.height, null);
		}
		
		//If player is not in a lit area, make screen darker (same with night and dusk)
		//Opacity based on distance to nearest light source
		g.setColor(new Color(0, 0, 0, 192));
		//g.fillRect(0, 0, Component.pixel.width, Component.pixel.height);
	}

	@Override
	public Location getLocation() {
		return new Location((int) Math.round(this.x), (int) Math.round(this.y));
	}

	public Location getBlockLocation() {
		return new Location(getBlockX(), getBlockY());
	}
}
