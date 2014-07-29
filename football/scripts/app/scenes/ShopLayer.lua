ShopLayer = class("ShopLayer", function()
	return display.newLayer()
end)

function ShopLayer:ctor()

	self.rect = display.newRect(display.width, display.height,{
                color=cc.c4f(0,0,0,126/255),
                fill=true,align=display.LEFT_BOTTOM,
                x=display.left,y=display.bottom,})
	:addTo(self)

	self._uiLayer = TouchGroup:create()
    self:addChild(self._uiLayer)
	self._widget = GUIReader:shareReader():widgetFromJsonFile("UI_SHOP/UI_SHOP.json")
    self._uiLayer:addWidget(self._widget)

    self.bg = self._uiLayer:getWidgetByName("Image_shop_bg")
    self.bg_size = self.bg:getContentSize()

    local shop_x = (display.width-self.bg_size.width)/2
    local shop_y = (display.height-self.bg_size.height)/2

    self.panel = self._uiLayer:getWidgetByName("Panel_shop")
    self.panel:setPosition(ccp(shop_x,shop_y))

    self.btn_one = self._uiLayer:getWidgetByName("Button_1")
    self.btn_five = self._uiLayer:getWidgetByName("Button_2")

    local function oneCallback(sender, eventType)
    	print("eventType:"..eventType)
        if eventType == 2 then
            -- data.num_lf = data.num_lf + 1
            -- CCUserDefault:sharedUserDefault():setIntegerForKey("num_lf",data.num_lf)
            -- print("wade num_lf:"..data.num_lf)

            -- call Java method
            local javaClassName = "com/wade/football/Football"
            local javaMethodName = "buyOne"
            local javaParams = {
                "How are you ?",
                "I'm great !",
                function(event)
                    printf("Java method callback value is [%s]", event)
                end
            }
            local javaMethodSig = "()V"
            luaj.callStaticMethod(javaClassName, javaMethodName, nil, javaMethodSig)
        end
    end

    local function fiveCallback(sender, eventType)
        if eventType == 2 then
            -- data.num_lf = data.num_lf + 6
            -- CCUserDefault:sharedUserDefault():setIntegerForKey("num_lf",data.num_lf)
            -- print("wade num_lf:"..data.num_lf)

            local javaClassName = "com/wade/football/Football"
            local javaMethodName = "buyFive"
            local javaParams = {
                "How are you ?",
                "I'm great !",
                function(event)
                    printf("Java method callback value is [%s]", event)
                end
            }
            local javaMethodSig = "()V"
            luaj.callStaticMethod(javaClassName, javaMethodName, nil, javaMethodSig)
        end
    end

    self.btn_one:addTouchEventListener(oneCallback)
    self.btn_five:addTouchEventListener(fiveCallback)
end

return ShopLayer