//
//  PhotoCommentViewController.swift
//  PhotoScroll
//
//  Created by Joan Angb on 12/08/2017.
//  Copyright © 2017 raywenderlich. All rights reserved.
//

import UIKit

class PhotoCommentViewController: UIViewController {
  
  @IBOutlet weak var imageView: UIImageView!
  @IBOutlet weak var scrollView: UIScrollView!
  @IBOutlet weak var nameTextField: UITextField!

    override func viewDidLoad() {
        super.viewDidLoad()

      NotificationCenter.default.addObserver(
        self,
        selector: #selector(PhotoCommentViewController.keyboardWillShow(_:)),
        name: Notification.Name.UIKeyboardWillShow,
        object: nil
      )
      NotificationCenter.default.addObserver(
        self,
        selector: #selector(PhotoCommentViewController.keyboardWillHide(_:)),
        name: Notification.Name.UIKeyboardWillHide,
        object: nil
      )    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
  deinit {
    NotificationCenter.default.removeObserver(self)
  }
  
  func adjustInsetForKeyboardShow(_ show: Bool, notification: Notification) {
    let userInfo = notification.userInfo ?? [:]
    let keyboardFrame = (userInfo[UIKeyboardFrameBeginUserInfoKey] as! NSValue).cgRectValue
    let adjustmentHeight = (keyboardFrame.height + 20) * (show ? 1 : -1)
    scrollView.contentInset.bottom += adjustmentHeight
    scrollView.scrollIndicatorInsets.bottom += adjustmentHeight
  }
  
  @objc func keyboardWillShow(_ notification: Notification) {
    adjustInsetForKeyboardShow(true, notification: notification)
  }
  
  @objc func keyboardWillHide(_ notification: Notification) {
    adjustInsetForKeyboardShow(false, notification: notification)
  }
  
  @IBAction func hideKeyboard(_ sender: AnyObject) {
    nameTextField.endEditing(true)
  }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
