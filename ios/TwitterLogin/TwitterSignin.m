//
//  TwitterSignin.m
//  TwitterLogin
//
//  Created by Victor Aliaga on 7/10/17.
//  Copyright © 2017 Facebook. All rights reserved.
//
#import <Fabric/Fabric.h>
#import <TwitterKit/TwitterKit.h>
#import "React/RCTEventDispatcher.h"
#import "TwitterSignin.h"

@implementation TwitterSignin

- (dispatch_queue_t)methodQueue
{
  return dispatch_get_main_queue();
}


RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(logIn:(RCTResponseSenderBlock)callback)
{
  [Fabric with:@[[Twitter class]]];
  
  [[Twitter sharedInstance] logInWithCompletion:^(TWTRSession *session, NSError *error) {
    if (error) {
      NSDictionary *body = @{
                             @"domain":error.domain,
                             @"code":@(error.code),
                             @"userInfo":[error.userInfo description]
                             };
      callback(@[body, [NSNull null]]);
    } else {
      NSDictionary *body = @{@"authToken": session.authToken,
                             @"authTokenSecret": session.authTokenSecret,
                             @"userID":session.userID,
                             @"userName":session.userName};
      callback(@[[NSNull null], body]);
      //            }];
    }
  }];
}

@end
