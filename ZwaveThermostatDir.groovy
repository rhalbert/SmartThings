/**
 *  Zwave Thermostat Manager
 * 
 * Credits and Kudos: 	this app is largely based on the more popular Thrmostat Director SA by Tim Slagle - 
 * 						many thanks to @slagle for his continued support. 
 * 						Without his brilliance, this app would not exist!
 * 
 * 
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
definition(
	name: "ZWave Thermostat Manager",
	namespace: "BD",
	author: "Bobby Dobrescu",
	description: "Adjust zwave thermostats based on a temperature range of a specific temperature sensor",
	category: "My apps",
	iconUrl: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png",
	iconX2Url: "http://icons.iconarchive.com/icons/icons8/windows-8/512/Science-Temperature-icon.png"
)

preferences {
    page name:"pageSetup"
    page name:"TemperatureSettings"
    page name:"ThermostatandDoors"
    page name:"ThermostatAway"
    page name:"Settings"

}

// Show setup page
def pageSetup() {

    def pageProperties = [
        name:       "pageSetup",
        title:      "",
        nextPage:   null,
        install:    true,
        uninstall:  true
    ]

	return dynamicPage(pageProperties) {
        section("General Settings") {
            href "TemperatureSettings", title: "Ambiance", description: "", state:greyedOut()
            href "ThermostatandDoors", title: "Disabled Mode", description: "", state: greyedOutTherm()
            href "ThermostatAway", title: "Away Mode", description: "", state: greyedOutTherm2()
			href "Settings", title: "Other Settings", description: "", state: greyedOutSettings()
            }
        section([title:"Options", mobileOnly:true]) {
            label title:"Assign a name", required:false
        }
    }
}

// Page - Temperature Settings	
def TemperatureSettings() {

    def sensor = [
        name:       "sensor",
        type:       "capability.temperatureMeasurement",
        title:      "Which Temperature Sensor?",
        multiple:   false,
        required:   true
    ]
    def thermostat = [
        name:       "thermostat",
        type:       "capability.thermostat",
        title:      "Which Thermostat?",
        multiple:   false,
        required:   true
    ]
    def setLow = [
        name:       "setLow",
        type:       "number",
        title:      "Low temp?",
        required:   true
    ]
    
    def cold = [
        name:       "cold",
        type:       "enum",
        title:		"Mode?",
    	required:   false,
		metadata:   [values:["auto", "heat", "cool", "off"]]

    ]

    def SetHeatingLow = [
        name:       "SetHeatingLow",
        type:       "number",
        title:		"Heating Temperature (degrees)",
        required:   false
    ]
    
     def SetCoolingLow = [
        name:       "SetCoolingLow",
        type:       "number",
        title:		"Cooling Temperature (degrees)",
        required:   false
    ]   
    
    def setHigh = [
        name:       "setHigh",
        type:       "number",
        title:      "High temp?",
        required:   true
    ]
    
    def hot = [
        name:       "hot",
        type:       "enum",
        title:		"Mode?",
        required:   false,
        metadata:   [values:["auto", "heat", "cool", "off"]]
    ]
    
    def SetHeatingHigh = [
        name:       "SetHeatingHigh",
        type:       "number",
        title:		"Heating Temperature (degrees)",
        required:   false
    ]
    
     def SetCoolingHigh = [
        name:       "SetCoolingHigh",
        type:       "number",
        title:		"Cooling Temperature (degrees)",
        required:   false
    ] 
  
    def pageName = "Ambiance"
    
    def pageProperties = [
        name:       "TemperatureSettings",
        title:      "",
        //nextPage:   "ThermostatandDoors"
    ]
    
    return dynamicPage(pageProperties) {
        section("Select the main thermostat") {
			input thermostat
		}
        section("Select the temperature sensor that will control your thermostat"){
			input sensor
		}
		section("When the temperature falls below this temperature..."){
			input setLow
		}
        section("Adjust the thermostat to the following settings:"){
			input cold
            input SetHeatingLow
            input SetCoolingLow
		}
        section("When the temperature raises above this temperature..."){
			input setHigh
		}
        section("Adjust the thermostat to the following settings:"){
			input hot
            input SetHeatingHigh
            input SetCoolingHigh
		}        
    }  
}

// Page - Disabled Mode
def ThermostatandDoors() {
  
    def doors = [
        name:       "doors",
        type:       "capability.contactSensor",
        title:      "Which Sensor(s)?",
        multiple:	true,
        required:   false
    ]
    
    def turnOffDelay = [
        name:       "turnOffDelay",
        type:       "decimal",
        title:		"Number of minutes",
        required:	false
    ]
    
    def pageName = "Thermostat and Doors"
    
    def pageProperties = [
        name:       "ThermostatandDoors",
        title:      "",
        //nextPage:   "ThermostatAway"
    ]

    return dynamicPage(pageProperties) {
        section("When one or more contact sensors open, then turn off the thermostat") {
			input doors
		}
		section("Wait this long before turning the thermostat off (defaults to 1 minute)") {
			input turnOffDelay
		}
    }
    
}

// Page - Thermostat Away
def ThermostatAway() {

    def modes2 = [
        name:		"modes2", 
        type:		"mode", 
        title: 		"Which Location Mode(s)?", 
        multiple: 	true, 
        required: 	false
    ]
    
    def away = [
        name:       "away",
        type:       "enum",
        title:		"Mode?",
        metadata:   [values:["auto", "heat", "cool", "off"]], 
        required: 	false
    ]

    def setAwayLow = [
        name:       "setAwayLow",
        type:       "decimal",
        title:      "Low temp?",
        required:   false
    ]
    
    def AwayCold = [
        name:       "AwayCold",
        type:       "enum",
        title:		"Mode?",
        metadata:   [values:["auto", "heat", "cool", "off"]],
        required: 	false,
    ]
    
    def setAwayHigh = [
        name:       "setAwayHigh",
        type:       "decimal",
        title:      "High temp?",
		required:   false
    ]
    
    def AwayHot = [
        name:       "AwayHot",
        type:       "enum",
        title:		"Mode?",
        required: 	false,
metadata:   [values:["auto", "heat", "cool", "off"]]
    ]
    
    def SetHeatingAway = [
        name:       "SetHeatingAway",
        type:       "number",
        title:		"Heating Temperature (degrees)",
        required: 	false
    ]   
    
    def SetCoolingAway = [
        name:       "SetCoolingAway",
        type:       "number",
        title:		"Cooling Temperature (degrees)",
        required: 	false
    ]     
    
    def fanAway = [
        name:       "fanAway",
        type:       "enum",
        title:		"Fan Mode?",
        metadata:   [values:["fanAuto", "fanOn", "fanCirculate"]],
        required: 	false
    ]

    def pageName = "Thermostat Away"
    
    def pageProperties = [
        name:       "ThermostatAway",
        title:      "",
        //nextPage:   "Settings"
    ]

    return dynamicPage(pageProperties) {
		
        section("When the Location Mode changes to 'Away'") {
   			input modes2
        }
           
        section("Adjust the thermostat to the following settings:") {
    		input away
            input fanAway                        
            input SetHeatingAway
			input SetCoolingAway
  		}	
        section("If the temperature falls below this temperature while away..."){
			input setAwayLow
		}
        
        section("Automatically adjust the thermostat to the following operating mode..."){
			input AwayCold
		}
        
        section("If the temperature raises above this temperature while away..."){
			input setAwayHigh
    	}
        section("Automatically adjust the thermostat to the following operating mode..."){
			input AwayHot
    	} 
	 }
}

// Show "Setup" page
def Settings() {
 
    def days = [
        name:       "days",
        type:       "enum",
        title:      "Only on certain days of the week",
        multiple:   true,
        required:   false,
        options: 	["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]
    ]
    
    def modes = [
        name:		"modes", 
        type:		"mode", 
        title: 		"Only when mode is", 
        multiple: 	true, 
        required: 	false
    ]
    
    def pageName = ""
    
    def pageProperties = [
        name:       "Settings",
        title:      "",
        //nextPage:   "pageSetup"
    ]

    return dynamicPage(pageProperties) {    
        
        section("Notifications") {
            input("recipients", "contact", title: "Send notifications to", multiple: true, required: false) {
	    paragraph 	"You may enter multiple phone numbers separated by semicolon (;) - E.G. 8045551122;8046663344"
            input "sms", "phone", title: "To this phone", multiple: false, required: false
            input "push", "bool", title: "Send Push Notification (optional)", required: false, defaultValue: false
            }
 }       
        section(title: "Restrictions", hideable: true) {
			href "timeIntervalInput", title: "Only during a certain time", description: getTimeLabel(starting, ending), state: greyedOutTime(starting, ending), refreshAfterSelection:true
			input days
			input modes
		}    
    }
    
}

def installed(){
	log.debug "Installed called with $settings"
	init()
}

def updated(){
	log.debug "Updated called with $settings"
	unsubscribe()
	init()
}

def init(){
	state.lastStatus = null
    runIn(60, "temperatureHandler")
    log.info "Temperature will be evaluated in one minute"
    subscribe(sensor, "temperature", temperatureHandler)
    if(modes2){
    	subscribe(location, modeAwayChange)
        subscribe(sensor, "temperature", modeAwayTempHandler)
    }
    if(doors){
            subscribe(doors, "contact.open", temperatureHandler)
            subscribe(doors, "contact.closed", doorCheck)
	}
}

def temperatureHandler(evt) {
	if(modeOk && daysOk && timeOk)  {
       	if (!modes2.contains(location.mode)){
            if(setLow > setHigh){
                def temp = setLow
                setLow = setHigh
                setHigh = temp
                log debug "Detected ${setLow} >  ${setHigh}. Auto-adjusting setting to  ${temp}"
            }
			if (doorsOk) {
           		def currentTemp = sensor.latestValue("temperature")
           		def currentMode = sensor.latestValue("thermostatMode")
                def currentHSP = sensor.latestValue("heatingSetpoint") 
                def currentCSP = sensor.latestValue("coolingSetpoint") 
                //log.info "Thermostat data is: ${currentMode} mode, ${currentTemp} temp, ${currentHSP} heatSP, ${currentCSP} coolSP"+
                //		 " and last app status is: ${lastStatus}"
                
                if (currentTemp < setLow) {
                    if (state.lastStatus == "one" || state.lastStatus == "two" || state.lastStatus == null){
                        state.lastStatus = "one" 
                         if (currentMode == "cool" || currentMode == "off") {
                     		def msg = "Changing your ${thermostat} mode to ${cold} because temperature is below ${setLow}"
                           	thermostat?."${cold}"()
                           	thermostat?.setHeatingSetpoint(SetHeatingLow)
                           	if (SetCoolingLow) thermostat?.setCoolingSetpoint(SetCoolingLow)
                           	thermostat?.poll()
                           	sendMessage(msg)
                        }
                     	else if  (SetHeatingLow < currentHSP) {
                            def msg = "Changing your ${thermostat} back to ${SetHeatingLow} because temperature is below ${setLow}"+
                            " the current Heating Setpoint is ${currentHSP}"
                     		thermostat?.setHeatingSetpoint(SetHeatingLow)
                     		if (SetCoolingLow) thermostat?.setCoolingSetpoint(SetCoolingLow)
                            thermostat?.poll()
                            sendMessage(msg)
                        }
                    }
                }                                     
                if (currentTemp > setHigh) {
                    if (state.lastStatus == "one" || state.lastStatus == "two" || state.lastStatus == null){
                        state.lastStatus = "two"
						if (currentMode == "heat" || currentMode == "off") {
                            def msg = "Changing your ${thermostat} mode to ${hot} because temperature is above ${setHigh}"
                        	thermostat?."${hot}"()
                        	if (SetHeatingHigh) thermostat?.setHeatingSetpoint(SetHeatingHigh)
                        	thermostat?.setCoolingSetpoint(SetCoolingHigh)
                        	thermostat?.poll()
                        	sendMessage(msg)                
                        }
                        else if (SetCoolingHigh < currentCSP) {
                            def msg = "Changing your ${thermostat} to ${SetCoolingHigh} because temperature is above ${setHigh} and"+
                            " the current Cooling Setpoint is ${currentCSP}"
                    		thermostat?.setCoolingSetpoint(SetCoolingHigh)
                     		if (SetHeatingHigh) thermostat?.setHeatingSetpoint(SetHeatingHigh)
                            thermostat?.poll()
                            sendMessage(msg)                           
                       	}
                   }     
                }
            }
            else{
                def delay = (turnOffDelay != null && turnOffDelay != "") ? turnOffDelay * 60 : 60
                log.debug("Detected open doors.  Checking door states again in ${delay} minutes")
                runIn(delay, "doorCheck")
            }
        }
	}
}

def modeAwayChange(evt){ 
	if(modeOk && daysOk && timeOk){
    	if (modes2){
            if(modes2.contains(location.mode)){
                    state.lastStatus = "away"
                    thermostat."${away}"()             
                    thermostat.setHeatingSetpoint(SetHeatingAway)
                    thermostat.setCoolingSetpoint(SetCoolingAway)
                    thermostat.setThermostatFanMode(fanAway)
                    def msg = "I changed your ${thermostat} mode to ${away} because Home Mode is set to Away"   
                    sendMessage(msg) 
                    log.debug "Running AwayChange because mode is now ${away} and last staus is ${lastStatus}"
            }
            else  {
        			state.lastStatus = null
                    temperatureHandler()
                    log.debug "Running Temperature Handler because Home Mode is no longer in away, and the last staus is ${lastStatus}"
			}
     	}
	}
}

def modeAwayTempHandler(evt) {

		if(lastStatus == "away"){
        	if(modes2.contains(location.mode)){
           		if (currentTemp < setAwayLow) {
					thermostat?."${Awaycold}"()
                    thermostat?.poll()
                    def msg = "I changed your ${thermostat} mode to ${Awaycold} because temperature is below ${setAwayLow}"
                    sendMessage(msg)
  				}
				if (currentTemp > setHigh) {
					thermostat?."${Awayhot}"()
                    thermostat?.poll()
					def msg = "I changed your ${thermostat} mode to ${Awayhot} because temperature is above ${setAwayHigh}"
                    sendMessage(msg)
  				}
             }
			Else {
        			state.lastStatus = null
            		temperatureHandler()
            		log.debug "Temp changed while staus is ${lastStatus} but the Home Mode is no longer in away. Resetting lastStatus"
        	}
	}
}

def doorCheck(evt){
	if (!doorsOk){
		log.debug("doors still open turning off ${thermostat}")
		def msg = "I changed your ${thermostat} mode to off because some doors are open"
		
        if (state.lastStatus != "off"){
        	thermostat?.off()
			sendMessage(msg)
		}
		state.lastStatus = "off"
	}

	else{
    	if (state.lastStatus == "off"){
			state.lastStatus = null
        }
        temperatureHandler()
	}
}

private void sendText(number, message) {
    if (sms) {
        def phones = sms.split("\\;")
        for (phone in phones) {
            sendSms(phone, message)
        }
    }
}

private void sendMessage(message) {
    log.debug "sending notification:  ${message}"
    if (recipients) { 
    //if (location.contactBookEnabled) {
        sendNotificationToContacts(message, recipients)
    } //else {
    if (push) {
        sendPush message
    } else {
            sendNotificationEvent(message)
    }
    if (sms) {
        sendText(sms, message)
    }
}
            

private getAllOk() {
	modeOk && daysOk && timeOk && doorsOk
}

private getModeOk() {
	def result = !modes || modes.contains(location.mode)
	log.trace "modeOk = $result"
	result
}

private getDoorsOk() {
	def result = !doors || !doors.latestValue("contact").contains("open")
	log.trace "doorsOk = $result"
	result
}


private getDaysOk() {
	def result = true
	if (days) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = days.contains(day)
	}
	log.trace "daysOk = $result"
	result
}

private getTimeOk() {
	def result = true
	if (starting && ending) {
		def currTime = now()
		def start = timeToday(starting).time
		def stop = timeToday(ending).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	}
    
    else if (starting){
    	result = currTime >= start
    }
    else if (ending){
    	result = currTime <= stop
    }
    
	log.trace "timeOk = $result"
	result
}

def getTimeLabel(starting, ending){

	def timeLabel = "Tap to set"
	
    if(starting && ending){
    	timeLabel = "Between" + " " + hhmm(starting) + " "  + "and" + " " +  hhmm(ending)
    }
    else if (starting) {
		timeLabel = "Start at" + " " + hhmm(starting)
    }
    else if(ending){
    timeLabel = "End at" + hhmm(ending)
    }
	timeLabel
}

private hhmm(time, fmt = "h:mm a")
{
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}
def greyedOut(){
	def result = ""
    if (sensor) {
    	result = "complete"	
    }
    result
}

def greyedOutTherm(){
	def result = ""
    if (thermostat) {
    	result = "complete"	
    }
    result
}


def greyedOutTherm2(){
	def result = ""
    if (modes2) {
    	result = "complete"	
    }
    result
}

def greyedOutSettings(){
	def result = ""
    if (starting || ending || days || modes || push) {
    	result = "complete"	
    }
    result
}

def greyedOutTime(starting, ending){
	def result = ""
    if (starting || ending) {
    	result = "complete"	
    }
    result
}

private anyoneIsHome() {
  def result = false

  if(people.findAll { it?.currentPresence == "present" }) {
    result = true
  }

  log.debug("anyoneIsHome: ${result}")

  return result
}

page(name: "timeIntervalInput", title: "Only during a certain time", refreshAfterSelection:true) {
		section {
			input "starting", "time", title: "Starting (both are required)", required: false 
			input "ending", "time", title: "Ending (both are required)", required: false 
		}
        }
