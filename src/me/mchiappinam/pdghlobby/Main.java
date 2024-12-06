package me.mchiappinam.pdghlobby;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getConsoleSender().sendMessage("§3[PDGHLobby] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobby] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHLobby] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHLobby] §2Acesse: http://pdgh.com.br/");
	}
	
	@EventHandler
	public void onClickPlayer(PlayerInteractEntityEvent e) {
	    if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR) {
	        if (e.getRightClicked() instanceof Player clickedPlayer) {
	            // Check if clickedPlayer is not already a passenger
	            if (!e.getPlayer().getPassengers().contains(clickedPlayer)) {
	                e.getPlayer().addPassenger(clickedPlayer);
	            }
	        }
	    }
	}

	@EventHandler
	public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
	    if (e.isSneaking()) {
	        if(e.getPlayer().getVehicle()!=null) {
	        		e.getPlayer().getVehicle().eject();
	        }
	    }
	}

	@EventHandler
	public void onPlayerThrow(PlayerInteractEvent e) {
	    if (e.getAction() == Action.LEFT_CLICK_AIR) {
	        if (!e.getPlayer().getPassengers().isEmpty()) {
	            Player thrower = e.getPlayer();

	            for (Entity passenger : thrower.getPassengers()) {
	                if (passenger instanceof Player toThrow) {
	                    thrower.removePassenger(toThrow); // Remove the player from the stack
	                    // Apply velocity to throw the player
	                    toThrow.setVelocity(e.getPlayer().getLocation().getDirection().normalize().multiply(6));
	                }
	            }
	        }
	    }
	}


		
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(final PlayerJoinEvent e) {
		//e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
		e.setJoinMessage(null);
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				e.getPlayer().sendMessage("§6"+ChatColor.BOLD+"➩"+ChatColor.DARK_AQUA+"ⓅⒹⒼⒽ Ⓜⓘⓝⓔⓒⓡⓐⓕⓣ Ⓢⓔⓡⓥⓔⓡⓢ");
				e.getPlayer().sendMessage("§6"+ChatColor.BOLD+"➩§eSeja bem vindo ao §bLOBBY §eda §3§lPDGH");
				e.getPlayer().sendMessage("§6"+ChatColor.BOLD+"➩§eEscolha um servidor para jogar com a bússola que está em seu inventário");
				e.getPlayer().sendMessage("§6"+ChatColor.BOLD+"➩§eBom aproveito! :)");
				e.getPlayer().sendMessage(" ");
			}
		}, 5L);
		for(Player p : getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, (byte) 30);
			p.sendMessage("§3[+] §a"+e.getPlayer().getName());
		}
	}
		
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerQuit(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		for (Player p : getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, (byte) 30);
			p.sendMessage("§3[-] §c"+e.getPlayer().getName());
		}
		/**final String file = e.getPlayer().getWorld().getWorldFolder().getPath().toString()+"/players/"+e.getPlayer().getDisplayName()+".dat";
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				deleteFile(file);
			}
		}, 5L);*/
	}
		
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerKick(PlayerKickEvent e) {
		e.setLeaveMessage(null);
		for (Player p : getServer().getOnlinePlayers()) {
			p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0F, (byte) 30);
			p.sendMessage("§3[-] §c"+e.getPlayer().getName());
		}
		/**final String file = e.getPlayer().getWorld().getWorldFolder().getPath().toString()+"/players/"+e.getPlayer().getDisplayName()+".dat";
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			public void run() {
				deleteFile(file);
			}
		}, 5L);*/
	}
	  
	/**@EventHandler(priority = EventPriority.MONITOR)
	private void onPlayerCommand(PlayerCommandPreprocessEvent e) {
		if(!e.getPlayer().hasPermission("pdgh.admin")) {
			if(!e.getMessage().startsWith("/logar")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(" ");
				e.getPlayer().sendMessage(ChatColor.RED+"➩Escolha um servidor para jogar!");
				e.getPlayer().sendMessage(" ");
			}
		}
	}*/
    
//    @EventHandler
//    public void onClickPlayer(PlayerInteractEntityEvent e) {
//    	if(e.getPlayer().getItemInHand().getType()==Material.AIR)
//    		if(e.getRightClicked() instanceof Player)
//    			if((((Player)e.getRightClicked()).getPassenger()==null)&&(e.getPlayer().getPassenger()==null))
//    				e.getPlayer().setPassenger(((Player)e.getRightClicked()));
//    }
//    
//    @EventHandler
//    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent e) {
//        if(e.getPlayer().isSneaking())
//        	for(Player p : getServer().getOnlinePlayers())
//        		if(p.getPassenger()==e.getPlayer())
//        			p.eject();
//    }
//    
//    @EventHandler
//    public void onPlayerThrow(PlayerInteractEvent e) {
//        if(e.getAction().equals(Action.LEFT_CLICK_AIR) && e.getPlayer().getPassenger()!=null) {
//            Player thrower = e.getPlayer();
//            Player toThrow = (Player) thrower.getPassenger();
//            thrower.eject();
//            toThrow.setVelocity(e.getPlayer().getLocation().getDirection().multiply(+3));
//        }
//    }
	
	/**public boolean deleteFile(String file) {
		boolean success = (new File(file)).delete();
		return success;
	}*/
	  
}