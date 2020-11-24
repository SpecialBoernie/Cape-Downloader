package net.capedownloader.src;

import net.capedownloader.src.utils.LoggerUtils;
import net.capedownloader.src.utils.PlayerUtils;
import net.labymod.api.LabyModAddon;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.ModColor;

import java.io.*;
import java.net.URL;
import java.util.List;

public class Main extends LabyModAddon {

    private static Main instance;

    final public String ADDON_NAME = "Cape Downloader";
    final public String ADDON_PREFIX = ModColor.cl("b") + ADDON_NAME + ModColor.cl("8") + " | " + ModColor.cl("7");;
    final public String ADDON_AUTHOR = "SocketConnection";
    final public String ADDON_VERSION = "v1.0";

    public String[] args;
    public String cmd;
    public String msg;

    @Override
    public void onEnable() {
        instance = this;

        LoggerUtils.info(ADDON_AUTHOR + " " + ADDON_VERSION + " by " + ADDON_AUTHOR);

        this.getApi().getEventManager().register(new MessageSendEvent() {
            @Override
            public boolean onSend(String s) {
                if(s.startsWith("!")) {
                    args = s.split(" ");
                    cmd = args[0].replaceAll("!", "");

                    StringBuilder builder = new StringBuilder();
                    for(int i = 1; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    msg = builder.toString().replaceAll("&", "§");

                    if(cmd.equalsIgnoreCase("cape")) {
                        if(args.length >= 2) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String resolvedUrl = LabyMod.getInstance().getUserManager().getUser(PlayerUtils.getUUID(args[1])).getCloakContainer().getResolvedURL();

                                    if(resolvedUrl != null) {
                                        try {
                                            URL url = new URL(resolvedUrl);

                                            InputStream in = new BufferedInputStream(url.openStream());
                                            ByteArrayOutputStream out = new ByteArrayOutputStream();

                                            byte[] buf = new byte[1024];
                                            int n = 0;
                                            while(-1 != (n = in.read(buf))) {
                                                out.write(buf, 0, n);
                                            }

                                            out.close();
                                            in.close();

                                            byte[] response = out.toByteArray();

                                            File f = new File(System.getProperty("user.home") + "\\Downloads\\" + args[1] + ".png");
                                            FileOutputStream fos = new FileOutputStream(f);
                                            fos.write(response);
                                            fos.close();

                                            LabyMod.getInstance().displayMessageInChat(ADDON_PREFIX + "You have successfully downloaded the cloak from " + args[1] + "!");
                                            LabyMod.getInstance().displayMessageInChat(ADDON_PREFIX + "It's in the following path: " + f.getAbsolutePath());
                                        } catch (IOException e) {
                                            LabyMod.getInstance().displayMessageInChat(ADDON_PREFIX + "Error: " + e.getMessage());
                                            LoggerUtils.error(e.getLocalizedMessage());
                                        }
                                    } else {
                                        LabyMod.getInstance().displayMessageInChat(ADDON_PREFIX + "This player does not exist.");
                                    }
                                }
                            }).start();
                        } else {
                            LabyMod.getInstance().displayMessageInChat(ADDON_PREFIX + "Usage: !cape <Player>");
                        }

                        return true;
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public static Main getInstance() {
        return instance;
    }

}
