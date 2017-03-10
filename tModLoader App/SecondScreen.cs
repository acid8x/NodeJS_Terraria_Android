using Terraria.ModLoader;

namespace SecondScreen
{
	class SecondScreen : Mod
	{
		public SecondScreen()
		{
			Properties = new ModProperties()
			{
				Autoload = true,
				AutoloadGores = true,
				AutoloadSounds = true
			};
		}
	}
}
