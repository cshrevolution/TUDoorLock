import io
import picamera
import logging
import socketserver
from threading import Condition
from http import server
from RPLCD.i2c import CharLCD
from mfrc522 import SimpleMFRC522
from pad4pi import rpi_gpio
import bluetooth
import RPi.GPIO as gpio
import time
import threading

gpio.setmode(gpio.BCM)

''' 블루투스 연결

server_socket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
port = 1
server_socket.bind(('B8:27:EB:8C:36:EF', port))
server_socket.listen(1)

print("Waiting connection")
client_socket, address = server_socket.accept()
print("Accepted connection from ", address)

client_socket.send("HelloWorld")

while True:
    data = client_socket.recv(1024)
    print("Received : %s" % data)

'''

trig_pin = 20
echo_pin = 21

servo_pin = 18

bell_pin = 16

cols_pin = [5, 22, 27, 17]
rows_pin = [6, 13, 19, 26]

keymap = [['1', '2', '3', '4'],
          ['5', '6', '7', '8'],
          ['9', '0', 'A', 'B'],
          ['C', 'D', 'E', 'F']]

lcd = CharLCD(i2c_expander='PCF8574', address=0x27, port=1, cols=16, rows=2, dotsize=8)

lcd.clear()

reader = SimpleMFRC522()

def cameraStreaming():


    PAGE="""\
    <html>
    <head>
    <title>picamera MJPEG streaming demo</title>
    </head>
    <body>
    <img src="stream.mjpg" width="640" height="480" />
    </body>
    </html>
    """

    # 카메라 스트리밍 IP주소:8000/index.html
    '''
    class StreamingOutput(object):
        def __init__(self):
            self.frame = None
            self.buffer = io.BytesIO()
            self.condition = Condition()

        def write(self, buf):
            if buf.startswith(b'\xff\xd8'):
                # New frame, copy the existing buffer's content and notify all
                # clients it's available
                self.buffer.truncate()
                with self.condition:
                    self.frame = self.buffer.getvalue()
                    self.condition.notify_all()
                self.buffer.seek(0)
            return self.buffer.write(buf)

    class StreamingHandler(server.BaseHTTPRequestHandler):
        def do_GET(self):
            if self.path == '/':
                self.send_response(301)
                self.send_header('Location', '/index.html')
                self.end_headers()
            elif self.path == '/index.html':
                content = PAGE.encode('utf-8')
                self.send_response(200)
                self.send_header('Content-Type', 'text/html')
                self.send_header('Content-Length', len(content))
                self.end_headers()
                self.wfile.write(content)
            elif self.path == '/stream.mjpg':
                self.send_response(200)
                self.send_header('Age', 0)
                self.send_header('Cache-Control', 'no-cache, private')
                self.send_header('Pragma', 'no-cache')
                self.send_header('Content-Type', 'multipart/x-mixed-replace; boundary=FRAME')
                self.end_headers()
                try:
                    while True:
                        with output.condition:
                            output.condition.wait()
                            frame = output.frame
                        self.wfile.write(b'--FRAME\r\n')
                        self.send_header('Content-Type', 'image/jpeg')
                        self.send_header('Content-Length', len(frame))
                        self.end_headers()
                        self.wfile.write(frame)
                        self.wfile.write(b'\r\n')
                except Exception as e:
                    logging.warning(
                        'Removed streaming client %s: %s',
                        self.client_address, str(e))
            else:
                self.send_error(404)
                self.end_headers()

    class StreamingServer(socketserver.ThreadingMixIn, server.HTTPServer):
        allow_reuse_address = True
        daemon_threads = True

    with picamera.PiCamera(resolution='640x480', framerate=24) as camera:
        output = StreamingOutput()
        camera.start_recording(output, format='mjpeg')
        try:
            address = ('', 8000)
            server = StreamingServer(address, StreamingHandler)
            server.serve_forever()
        finally:
            camera.stop_recording()
    '''

factory = rpi_gpio.KeypadFactory()
keypad = factory.create_keypad(keypad=keymap, row_pins=rows_pin, col_pins=cols_pin)

def printKey(key):
    print(key)

keypad.registerKeyPressHandler(printKey)
