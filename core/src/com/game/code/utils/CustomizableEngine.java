package com.game.code.utils;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityUserDataSetter;
import com.game.code.EntityBuilding.HasFriendsComponentSetter;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import com.game.code.Socket.*;
import com.game.code.systems.Actions.AimingSystem;
import com.game.code.systems.Actions.MovementSystem;
import com.game.code.systems.Actions.ShootingSystem;
import com.game.code.systems.Attachment.CameraFollowingSystem;
import com.game.code.systems.Attachment.FollowCameraSystem;
import com.game.code.systems.Attachment.FollowCursorSystem;
import com.game.code.systems.Attachment.Inherit.InheritAngleSystem;
import com.game.code.systems.Attachment.Inherit.InheritColorSystem;
import com.game.code.systems.Attachment.Inherit.InheritDeathSystem;
import com.game.code.systems.Box2d.ConnectionSystem;
import com.game.code.systems.Box2d.Contact.CollusionSystem;
import com.game.code.systems.Box2d.Contact.ContactBreakListener;
import com.game.code.systems.Box2d.Contact.ContactDamageListener;
import com.game.code.systems.Box2d.Contact.ContactEventSystem;
import com.game.code.systems.Box2d.DestroyingSystem;
import com.game.code.systems.Box2d.PhysicsSystem;
import com.game.code.systems.Box2d.listeners.BodyHider;
import com.game.code.systems.Box2d.listeners.LifeSpanListener;
import com.game.code.systems.Death.DamagingSystem;
import com.game.code.systems.Death.LowHealthDeathSystem;
import com.game.code.systems.Death.RemoveAfterDeathListener;
import com.game.code.systems.Death.listeners.FadeAfterDeathListener;
import com.game.code.systems.Death.listeners.InvincibilityListener;
import com.game.code.systems.Death.listeners.MaxHealthListener;
import com.game.code.systems.Death.listeners.RespawnListener;
import com.game.code.systems.DistinctZIndexSystem;
import com.game.code.systems.HUD.listeners.AboveNameListener;
import com.game.code.systems.Input.AimingInputSystem;
import com.game.code.systems.Input.MovementInputSystem;
import com.game.code.systems.Input.ShootingInputSystem;
import com.game.code.systems.Render.CameraInvisibleSystem;
import com.game.code.systems.Render.RenderingSystem;
import com.game.code.systems.ScrollSystem;
import com.game.code.systems.Sound.SoundSystem;
import com.game.code.systems.Sound.listeners.*;
import com.game.code.systems.Summon.SummoningSystem;
import com.game.code.systems.Summon.SummonsAfterCollusionSystem;
import com.game.code.systems.Summon.SummonsWhileMovingSystem;
import com.game.code.systems.Summon.listeners.SummoningAfterDeathListener;
import com.game.code.systems.Summon.listeners.SummonsAfterRemoveListener;
import io.socket.client.Socket;

public class CustomizableEngine extends PooledEngine {

    private final ComponentInitializer componentInitializer;

    public CustomizableEngine(ComponentInitializer componentInitializer) {
        this.componentInitializer = componentInitializer;
    }

    public void includeBasic(EntitySummonerProvider entitySummonerProvider, World world, Viewport viewport, Batch batch) {
        this.addSystem(new RenderingSystem(viewport.getCamera(), batch));
        this.addSystem(new CameraInvisibleSystem(viewport.getCamera()));
        this.addSystem(new FollowCameraSystem(viewport.getCamera()));
        this.addSystem(new DistinctZIndexSystem());

        includeInput(viewport,  entitySummonerProvider);

        includeInheritance();
        
        includeDeath();

        includeBox2D(world);

        includeSound();

        this.addSystem(new SummonsWhileMovingSystem());
        this.addSystem(new ScrollSystem());
        
        this.addSystem(new SummoningSystem(entitySummonerProvider));

        this.addEntityListener(AboveNameListener.FAMILY, new AboveNameListener(this, componentInitializer));
        this.addEntityListener(InvincibilityListener.FAMILY, new InvincibilityListener());
        this.addEntityListener(RespawnListener.FAMILY, new RespawnListener(this));
        this.addEntityListener(MaxHealthListener.FAMILY, new MaxHealthListener());
    }
    
    private void includeInput(Viewport viewport, EntitySummonerProvider entitySummonerProvider) {
        this.addSystem(new MovementInputSystem());
        this.addSystem(new MovementSystem());
        this.addSystem(new CameraFollowingSystem(viewport.getCamera()));
        this.addSystem(new FollowCursorSystem(viewport));

        this.addSystem(new AimingInputSystem(viewport));
        this.addSystem(new AimingSystem());

        this.addSystem(new ShootingInputSystem());
        this.addSystem(new ShootingSystem(entitySummonerProvider.provide(SummonerType.Projectile)));
    }

    private void includeInheritance() {
        this.addSystem(new ConnectionSystem());
        this.addSystem(new InheritAngleSystem());
        this.addSystem(new InheritColorSystem());
        this.addSystem(new InheritDeathSystem());
    }

    private void includeDeath() {
        this.addSystem(new DamagingSystem());

        this.addSystem(new LowHealthDeathSystem());
        this.addEntityListener(FadeAfterDeathListener.FAMILY, new FadeAfterDeathListener(this));
        this.addEntityListener(SummoningAfterDeathListener.FAMILY, new SummoningAfterDeathListener(this));

        this.addEntityListener(RemoveAfterDeathListener.FAMILY, new RemoveAfterDeathListener(this));
    }

    private void includeBox2D(World world) {
        this.addEntityListener(EntityUserDataSetter.FAMILY, new EntityUserDataSetter());
        this.addEntityListener(HasFriendsComponentSetter.FAMILY, new HasFriendsComponentSetter());
        this.addEntityListener(BodyHider.FAMILY, new BodyHider());
        
        this.addSystem(new DestroyingSystem(world));
        this.addSystem(new PhysicsSystem(world));

        this.addEntityListener(LifeSpanListener.FAMILY, new LifeSpanListener(this));

        this.addEntityListener(ContactDamageListener.FAMILY, new ContactDamageListener(this));
        this.addEntityListener(ContactEventSystem.FAMILY, new ContactEventSystem());
        this.addEntityListener(ContactBreakListener.FAMILY, new ContactBreakListener(this));
        
        this.addSystem(new CollusionSystem());
        
        this.addSystem(new SummonsAfterCollusionSystem());
        this.addEntityListener(SummonsAfterRemoveListener.FAMILY,new SummonsAfterRemoveListener(this));
    }

    public void includeSound() {
        this.addEntityListener(SoundSearcher.FAMILY, new SoundSearcher(this));

        this.addEntityListener(AimingSoundListener.FAMILY, new AimingSoundListener(this));
        this.addEntityListener(ContactSoundListener.FAMILY, new ContactSoundListener(this));
        this.addEntityListener(MovementSoundListener.FAMILY, new MovementSoundListener(this));
        this.addEntityListener(DamagedSoundListener.FAMILY, new DamagedSoundListener(this));
        this.addEntityListener(DeathSoundListener.FAMILY, new DeathSoundListener(this));
        this.addEntityListener(ShootingSoundListener.FAMILY, new ShootingSoundListener(this));
        this.addEntityListener(AppearSoundListener.FAMILY, new AppearSoundListener(this));

        this.addSystem(new SoundSystem());
    }
    
    public void includeServerInteractions(Socket socket, PlayerCreator playerCreator) {
        this.addSystem(new OtherSocketShootingSystem(socket));
        this.addSystem(new SocketShootingInputSystem(socket));

        this.addSystem(new OtherSocketAimingSystem(socket));
        this.addSystem(new SocketAimingSystem(socket));

        this.addSystem(new OtherSocketMovementSystem(socket));
        this.addSystem(new SocketMovementSystem(socket));

        this.addSystem(new OtherSocketDamagingSystem(socket));
        this.addEntityListener(SocketDamagingSystem.FAMILY, new SocketDamagingSystem(socket));

        this.addSystem(new SocketPlayerCreationSystem(socket, playerCreator, componentInitializer));
    }
}
