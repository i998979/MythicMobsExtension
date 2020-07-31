package com.gmail.berndivader.mythicmobsext.volatilecode.v1_16_R1.pathfindergoals;

import com.gmail.berndivader.mythicmobsext.Main;

import net.minecraft.server.v1_16_R1.Block;
import net.minecraft.server.v1_16_R1.BlockDoor;
import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.EntityInsentient;
import net.minecraft.server.v1_16_R1.GameRules;
import net.minecraft.server.v1_16_R1.IBlockData;
import net.minecraft.server.v1_16_R1.Material;
import net.minecraft.server.v1_16_R1.Navigation;
import net.minecraft.server.v1_16_R1.PathEntity;
import net.minecraft.server.v1_16_R1.PathPoint;
import net.minecraft.server.v1_16_R1.PathfinderGoal;

public class PathfinderGoalInteractDoor extends PathfinderGoal {
	protected EntityInsentient a;
	protected BlockPosition b = BlockPosition.ZERO;
	protected BlockDoor c;
	boolean d, bl1;
	float e, f;
	double dx, dy, dz;

	public PathfinderGoalInteractDoor(EntityInsentient e, boolean bl1) {
		this.a = e;
		this.bl1 = bl1;
		if (!(e.getNavigation() instanceof Navigation))
			Main.logger.warning("No navigation mob");
		((Navigation) e.getNavigation()).a(true);
	}

	@Override
	public boolean a() {
		if (!this.a.positionChanged || (bl1 && !this.a.world.getGameRules().getBoolean(GameRules.MOB_GRIEFING)))
			return false;
		Navigation n1 = (Navigation) this.a.getNavigation();
		PathEntity pe1 = n1.k();
		if (pe1 == null || pe1.b() || !n1.f())
			return false;
		for (int i1 = 0; i1 < Math.min(pe1.f() + 2, pe1.e()); i1++) {
			PathPoint pp1 = pe1.a(i1);
			this.b = new BlockPosition(pp1.a, pp1.b + 1, pp1.c);
			if (this.a.g(this.b.getX(), this.a.locY(), this.b.getZ()) > 2.25)
				continue;
			this.c = this.a(this.b);
			if (this.c == null)
				continue;
			return true;
		}
		this.b = new BlockPosition(this.a.locX(), this.a.locY(), this.a.locZ()).up();
		this.c = this.a(this.b);
		return this.c != null;
	}

	@Override
	public boolean b() {
		return !this.d;
	}

	@Override
	public void c() {
		this.d = false;
		this.e = (float) ((double) ((float) this.b.getX() + 0.5f) - this.a.locX());
		this.f = (float) ((double) ((float) this.b.getZ() + 0.5f) - this.a.locZ());
	}

	@Override
	public void e() {
		float f3 = (float) ((double) ((float) this.b.getX() + 0.5f) - this.a.locX());
		float f4 = this.e * f3 + this.f * ((float) ((double) ((float) this.b.getZ() + 0.5f) - this.a.locZ()));
		if (f4 < 0.0f)
			this.d = true;
	}

	private BlockDoor a(BlockPosition bp1) {
		IBlockData bd1 = this.a.world.getType(bp1);
		Block b1 = bd1.getBlock();
		if ((b1 instanceof BlockDoor) && bd1.getMaterial() == Material.WOOD)
			return (BlockDoor) b1;
		return null;
	}
}
