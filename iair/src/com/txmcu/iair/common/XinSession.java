package com.txmcu.iair.common;

import java.util.HashMap;
import java.util.Map; public class XinSession {
 
 @SuppressWarnings("unchecked")
 private Map _objectContainer;
 
 private static XinSession session;
 
 /**
  * 全局信息保存类
  */
 //Attention here, DO NOT USE keyword 'new' to create this object.
 //Instead, use getSession method.
 @SuppressWarnings("unchecked")
 private XinSession(){
  _objectContainer = new HashMap();
 }
 
 public static XinSession getSession(){
  
  if(session == null){
   session = new XinSession();
   return session;
  }else{
   return session;
  }
 }
 
 @SuppressWarnings("unchecked")
 public void put(Object key, Object value){
 
  _objectContainer.put(key, value);
 }
 public Object get(Object key){
  
  return _objectContainer.get(key);
 }
 public void cleanUpSession(){
  _objectContainer.clear();
 }
 public void remove(Object key){
  _objectContainer.remove(key);
 }
}
 