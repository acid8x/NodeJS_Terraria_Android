using Terraria;
using Terraria.Graphics.Effects;
using Terraria.Graphics.Shaders;
using Terraria.ID;
using Terraria.ModLoader;
using System.Net;
using System.Text;
using Quobject.SocketIoClientDotNet.Client;
using System;
using Microsoft.Xna.Framework.Graphics;
using System.IO;
using Microsoft.Xna.Framework;

namespace SecondScreen
{
    public class completeItem
    {
        public int id;
        public int stack;
        public string base64;
    }

    public class stackOnly
    {
        public int id;
        public int stack;
    }

    public class SecondScreen : Mod
    {
        public struct Inventory
        {
            public int id;
            public int stack;
            public string base64;

            public void set(int a, int b, string c)
            {
                id = a;
                stack = b;
                base64 = c;
            }
        }

        public static Socket socket;
        public long timer;
        public Inventory[] inventory = new Inventory[50];
        public modPlayer myPlayer = null;

        public SecondScreen()
        {
            Properties = new ModProperties()
            {
                Autoload = true,
                AutoloadGores = true,
                AutoloadSounds = true
            };
        }

        public override void Load()
        {
            for (int i = 0; i < 50; i++) inventory[i].set(0, 0, "");
            base.Load();
        }

        public override void PostDrawInterface(SpriteBatch spriteBatch)
        {
            if (Main.LocalPlayer.active && myPlayer == null) {
                myPlayer = Main.LocalPlayer.GetModPlayer<modPlayer>(this);
                socket = IO.Socket("http://127.0.0.1:2222");
            }
            if (myPlayer != null && now() - timer > 500)
            {
                for (int i = 0; i < 50; i++)
                {
                    if (Main.LocalPlayer.inventory[i].netID != inventory[i].id)
                    {
                        inventory[i].id = Main.LocalPlayer.inventory[i].netID;
                        inventory[i].stack = Main.LocalPlayer.inventory[i].stack;
                        inventory[i].base64 = getBase64String(Main.LocalPlayer.inventory[i]);
                        String s = "" + i + "," + inventory[i].stack + "," + inventory[i].base64;
                        socket.Emit("completeItem", s);
                    } else if (Main.LocalPlayer.inventory[i].stack != inventory[i].stack)
                    {
                        inventory[i].stack = Main.LocalPlayer.inventory[i].stack;
                        String s = "" + i + "," + inventory[i].stack;
                        socket.Emit("stackOnly", s);
                    }
                }
                timer = now();
            }
            base.PostDrawInterface(spriteBatch);
        }

        public long now()
        {
            return DateTime.Now.Ticks / TimeSpan.TicksPerMillisecond;
        }

        public string getBase64String(Item i)
        {
            Stream stream = System.IO.File.Create("file.png");
            Main.itemTexture[i.netID].SaveAsPng(stream, Main.itemTexture[i.netID].Width, Main.itemTexture[i.netID].Height);
            stream.Dispose();
            return Convert.ToBase64String(System.IO.File.ReadAllBytes(@"file.png"));
        }
    }
}