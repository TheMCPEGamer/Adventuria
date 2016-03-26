package net.adventuria.listeners;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.adventuria.Component;
import net.adventuria.block.BlockType;
import net.adventuria.gui.inventory.Inventory;
import net.adventuria.inputs.Mouse;
import net.adventuria.location.Location;

public class AdventuriaMouseListener implements MouseListener, MouseMotionListener, MouseWheelListener {

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (!Inventory.isOpen) {
			if (e.getWheelRotation() < 0) {
				if (Inventory.selected > 0) {
					Inventory.selected -= 1;
				} else if (Inventory.selected == 0) {
					Inventory.selected = Inventory.invLength - 1;
				}
			} else if (e.getWheelRotation() > 0) {
				if (Inventory.selected < Inventory.invLength - 1) {
					Inventory.selected += 1;
				} else if (Inventory.selected == Inventory.invLength - 1) {
					Inventory.selected = 0;
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Mouse.setLocation(new Location(e.getX(), e.getY()));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse.setLocation(new Location(e.getX(), e.getY()));
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		Mouse.setLocation(new Location(e.getX(), e.getY()));
		if (e.getButton() == 1) {
			Mouse.setLeftButton(true);

		}

		else if (e.getButton() == 3) {
			Mouse.setRightButton(true);
		}

		if (Inventory.isOpen) {
			if (e.getButton() == 1) {
				if (Inventory.currentHeldItemID == BlockType.AIR) {
					for (int i = 0; i < Inventory.invBag.length; i++) {
						if (Inventory.invBag[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.invBag[i].ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
								Inventory.currentHeldItemID = Inventory.invBag[i].ID;
								Inventory.currentHeldItemCount = Inventory.invBag[i].Count;

								Inventory.invBag[i].ID = BlockType.AIR;
								Inventory.invBag[i].Count = 0;
							}
						}
					}
					for (int i = 0; i < Inventory.invHotBar.length; i++) {
						if (Inventory.invHotBar[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.invHotBar[i].ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
								Inventory.currentHeldItemID = Inventory.invHotBar[i].ID;
								Inventory.currentHeldItemCount = Inventory.invHotBar[i].Count;

								Inventory.invHotBar[i].ID = BlockType.AIR;
								Inventory.invHotBar[i].Count = 0;
							}
						}
					}
					for(int i = 0; i < Inventory.craftingGrid.length; i++)
					{
						if (Inventory.craftingGrid[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.craftingGrid[i].ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
								Inventory.currentHeldItemID = Inventory.craftingGrid[i].ID;
								Inventory.currentHeldItemCount = Inventory.craftingGrid[i].Count;

								Inventory.craftingGrid[i].ID = BlockType.AIR;
								Inventory.craftingGrid[i].Count = 0;
							}
						}
					}
					
					if(Inventory.craftingOutput.contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize)))
					{
						if ((Inventory.craftingOutput.ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
							Inventory.currentHeldItemID = Inventory.craftingOutput.ID;
							Inventory.currentHeldItemCount = Inventory.craftingOutput.Count;

							Inventory.craftingOutput.ID = BlockType.AIR;
							Inventory.craftingOutput.Count = 0;
						}
					}
				} else if ((Inventory.isOpen) && (Inventory.currentHeldItemID != BlockType.AIR)) {
					for (int i = 0; i < Inventory.invBag.length; i++) {
						if (Inventory.invBag[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.invBag[i].ID == BlockType.AIR) && (Inventory.currentHeldItemID != BlockType.AIR) && (Inventory.currentHeldItemCount != 0)) {
								Inventory.invBag[i].ID = Inventory.currentHeldItemID;
								Inventory.invBag[i].Count = Inventory.currentHeldItemCount;

								Inventory.currentHeldItemID = BlockType.AIR;
								Inventory.currentHeldItemCount = 0;
							}
						}
					}
					for (int i = 0; i < Inventory.invHotBar.length; i++) {
						if (Inventory.invHotBar[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.invHotBar[i].ID == BlockType.AIR) && (Inventory.currentHeldItemID != BlockType.AIR) && (Inventory.currentHeldItemCount != 0)) {
								Inventory.invHotBar[i].ID = Inventory.currentHeldItemID;
								Inventory.invHotBar[i].Count = Inventory.currentHeldItemCount;

								Inventory.currentHeldItemID = BlockType.AIR;
								Inventory.currentHeldItemCount = 0;
							}
						}
					}
					for(int i = 0; i < Inventory.craftingGrid.length; i++)
					{
						if (Inventory.craftingGrid[i].contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize))) {
							if ((Inventory.craftingGrid[i].ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
								Inventory.currentHeldItemID = Inventory.craftingGrid[i].ID;
								Inventory.currentHeldItemCount = Inventory.craftingGrid[i].Count;

								Inventory.craftingGrid[i].ID = BlockType.AIR;
								Inventory.craftingGrid[i].Count = 0;
							}
						}
					}
					
					if(Inventory.craftingOutput.contains(new Point(Mouse.getX() / Component.pixelSize, Mouse.getY() / Component.pixelSize)))
					{
						if ((Inventory.craftingOutput.ID != BlockType.AIR) && (Inventory.currentHeldItemID == BlockType.AIR) && (Inventory.currentHeldItemCount == 0)) {
							Inventory.currentHeldItemID = Inventory.craftingOutput.ID;
							Inventory.currentHeldItemCount = Inventory.craftingOutput.Count;

							Inventory.craftingOutput.ID = BlockType.AIR;
							Inventory.craftingOutput.Count = 0;
						}
					}
				}
			} else if (e.getButton() == 3) {
				if (Inventory.currentHeldItemID != BlockType.AIR) {
					if (Inventory.currentHeldItemCount > 1)
					{
						//TODO
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Mouse.setLocation(new Location(e.getX(), e.getY()));
		if (e.getButton() == 1) {
			Mouse.setLeftButton(false);
		}

		else if (e.getButton() == 3) {
			Mouse.setRightButton(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
