/**
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package autosaveworld.config;

import java.io.File;

import autosaveworld.config.loader.Config;
import autosaveworld.config.loader.ConfigOption;
import autosaveworld.core.GlobalConstants;

public class AutoSaveWorldConfigMSG implements Config {

	@ConfigOption(path = "broadcast.pre")
	public String messageSaveBroadcastPre = "&9Автосохранение";
	@ConfigOption(path = "broadcast.post")
	public String messageSaveBroadcastPost = "&9Автосохранение Успешно";
	@ConfigOption(path = "broadcastbackup.pre")
	public String messageBackupBroadcastPre = "&9Автобэкап";
	@ConfigOption(path = "broadcastbackup.post")
	public String messageBackupBroadcastPost = "&9Автобэкап успешено";
	@ConfigOption(path = "broadcastpurge.pre")
	public String messagePurgeBroadcastPre = "&9Автоматическая очистка";
	@ConfigOption(path = "broadcastpurge.post")
	public String messagePurgeBroadcastPost = "&9Автоматическая очистка успешна";
	@ConfigOption(path = "autorestart.restarting")
	public String messageAutoRestart = "&9Сервер перезапускается";
	@ConfigOption(path = "autorestart.countdown")
	public String messageAutoRestartCountdown = "&9Сервер перезагрузиться через {SECONDS} секунд";
	@ConfigOption(path = "worldregen.kickmessage")
	public String messageWorldRegenKick = "&9Сервер пересоздает карту перезайдите позже.";
	@ConfigOption(path = "insufficentpermissions")
	public String messageInsufficientPermissions = "&cУ вас нет доступа к этой комманде.";

	@Override
	public File getFile() {
		return GlobalConstants.getMessageConfigPath();
	}

}
