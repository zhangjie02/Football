
local MyApp = class("MyApp", cc.mvc.AppBase)

function MyApp:ctor()
    MyApp.super.ctor(self)
end

function MyApp:run()
    CCFileUtils:sharedFileUtils():addSearchPath("res/")
    display.addSpriteFramesWithFile(data.texture.fileName, data.texture.imageName)
    audio.preloadMusic(data.sound.bg)

    -- preload all sounds
    for k, v in pairs(data.sound) do
        audio.preloadSound(v)
    end
    
    self:enterScene("MenuScene")
end

function MyApp:enterMenuScene()
    self:enterScene("MenuScene", nil, "fade", 0.6, display.COLOR_WHITE)
end

function MyApp:enterChooseLevelScene()
    self:enterScene("ChooseLevelScene", nil, "fade", 0.6, display.COLOR_WHITE)
end

function MyApp:playLevel(levelIndex)
    self:enterScene("PlayLevelScene", {levelIndex}, "fade", 0.6, display.COLOR_WHITE)
end

function MyApp:enterHelpScene()
    self:enterScene("HelpScene", nil, "fade", 0.6, display.COLOR_WHITE)
end

function MyApp:enterShopScene()
    self:enterScene("ShopScene", nil, "fade", 0.6, display.COLOR_WHITE)
end

return MyApp
