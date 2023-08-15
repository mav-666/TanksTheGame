package com.game.code.utils;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.code.EntityBuilding.ComponentInitializer;
import com.game.code.EntityBuilding.EntityBuilder;
import com.game.code.EntityBuilding.EntityUserDataSetter;
import com.game.code.EntityBuilding.HasFriendsComponentSetter;
import com.game.code.EntityBuilding.Summoners.EntitySummonerProvider;
import com.game.code.EntityBuilding.Summoners.SummonerType;
import com.game.code.EntityBuilding.battlefiled.PlayerCreator;
import com.game.code.Socket.*;
import com.game.code.systems.*;
import com.game.code.systems.listeners.*;
import com.game.code.systems.listeners.Box2D.BodyHider;
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

        this.addSystem(new RemoveAfterDeathSystem());
    }

    private void includeBox2D(World world) {
        this.addEntityListener(EntityUserDataSetter.FAMILY, new EntityUserDataSetter());
        this.addEntityListener(HasFriendsComponentSetter.FAMILY, new HasFriendsComponentSetter());
        this.addEntityListener(BodyHider.FAMILY, new BodyHider());
        
        this.addSystem(new DestroyingSystem(world));
        this.addSystem(new PhysicsSystem(world));

        this.addEntityListener(LifeSpanListener.FAMILY, new LifeSpanListener(this));

        this.addSystem(new ContactDamageSystem());
        this.addSystem(new ContactEventSystem());
        this.addSystem(new ContactBreakSystem());
        
        this.addSystem(new CollusionSystem());
        
        this.addSystem(new SummonsAfterCollusionSystem());
        this.addEntityListener(SummonsAfterRemoveListener.FAMILY,new SummonsAfterRemoveListener(this));
    }
    
    public void includeServerInteractions(Socket socket, PlayerCreator playerCreator) {
        this.addSystem(new OtherSocketShootingSystem(socket));
        this.addSystem(new SocketShootingInputSystem(socket));

        this.addSystem(new OtherSocketAimingSystem(socket));
        this.addSystem(new SocketAimingSystem(socket));

        this.addSystem(new OtherSocketMovementSystem(socket));
        this.addSystem(new SocketMovementSystem(socket));

        this.addSystem(new SocketPlayerCreationSystem(socket, playerCreator, componentInitializer));
    }
    
    public void includeDebug(EntityBuilder entityBuilder, World world, Viewport viewport) {
        EntitySystem profile = new GLProfileSystem();
        profile.setProcessing(false);
        this.addSystem(profile);

        this.addSystem(new DebugSummonSystem(entityBuilder, viewport));

        EntitySystem debug = new Box2dDebugSystem(world, viewport);
        debug.setProcessing(false);
        this.addSystem(debug);

    }
}
