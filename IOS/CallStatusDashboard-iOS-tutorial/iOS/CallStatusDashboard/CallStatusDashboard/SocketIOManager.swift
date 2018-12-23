//
//  SocketIOManager.swift
//  CallStatusDashboard
//
//  Created by Joan Angb on 30/08/2017.
//  Copyright © 2017 Sam Agnew. All rights reserved.
//

import Foundation

import SocketIO

class SocketIOManager: NSObject {
    
    static let sharedInstance = SocketIOManager()
    
    var socket = SocketIOClient(socketURL: URL(string: "https://3b688877.ngrok.io")!, config: [.log(false), .forcePolling(true)])
    
    override init() {
        super.init()
        
        socket.on("test") { dataArray, ack in
            print(dataArray)
        }
        
        
        socket.on("status update") { dataArray, ack in
            NotificationCenter.default
                .post(name: Notification.Name(rawValue: "callStatusUpdateNotification"), object: dataArray[0] as? [String: AnyObject])
        }
        
    }
    
    func establishConnection() {socket.connect()}
    
    func closeConnection() {socket.disconnect()}
    
}
