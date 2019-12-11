import board
import digitalio
import busio
import adafruit_bme280 as bme280 
import python_tsl2591 as tsl2591 
import RPi.GPIO as GPIO #Can only be run on a raspberry pi
import adafruit_mcp3xxx.mcp3008 as MCP
from adafruit_mcp3xxx.analog_in import AnalogIn
import nonBoard
import time
import math
## SETUP

i2c = busio.I2C(board.SCL, board.SDA)
tsl2591_sensor = tsl2591.tsl2591()
bme280_sensor = bme280.Adafruit_BME280_I2C(i2c)
bme280_sensor.sea_level_pressure = 1013.7

nonBoard.average_and_send()
while True:
    with nonBoard.lock:
        #init data required for sampling sound
        start_milli = int(round(time.time() * 1000))
        peak_to_peak = 0
        signal_max = 0
        signal_min = 32768
        sample_window = 150
        
        (full, ir) = tsl2591_sensor.get_full_luminosity() #full and ir spectrum
        lux = tsl2591_sensor.calculate_lux(full, ir)
        nonBoard.full_total += full
        nonBoard.ir_total += ir
        nonBoard.lux_total += lux
        nonBoard.gain_total += tsl2591_sensor.get_current()["gain"]
        nonBoard.temperature_total += bme280_sensor.temperature
        nonBoard.humidity_total += bme280_sensor.humidity
        nonBoard.pressure_total += bme280_sensor.pressure
        nonBoard.altitude_total += bme280_sensor.altitude
        nonBoard.counter+=1
                
    time.sleep(0.1)


