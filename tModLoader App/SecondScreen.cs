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

namespace SecondScreen
{
	class SecondScreen : Mod
	{
		public static Socket socket;
        public ModPlayer me = null;

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
            socket = IO.Socket("http://127.0.0.1:2222");
            base.Load();
        }

        public override void PostDrawInterface(SpriteBatch spriteBatch)
        {
            if (me == null) me = Main.LocalPlayer.GetModPlayer<ModPlayer>(this);
            string s = "";
            foreach (Item i in Main.LocalPlayer.inventory)
            {
                if (s != "") s += ", ";
                s += i.name;
            }
            socket.Emit("message", s);
            base.PostDrawInterface(spriteBatch);
        }
	}
}