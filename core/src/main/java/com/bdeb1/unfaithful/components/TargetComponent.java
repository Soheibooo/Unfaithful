/*
 * Copyright 2018 Soheib El-Harrache.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bdeb1.unfaithful.components;

import com.badlogic.ashley.core.Component;

/**
 *
 * @author Soheib El-Harrache
 */
public class TargetComponent implements Component {
    public static final int STATE_UNSUSPICIOUS = 0;
    public static final int STATE_SUSPICIOUS = 1;
    public static final int STATE_FRENZY = 2;
    public static final int STATE_DONE = 3;
    
    public static final int ACTION_TALKING = 10;
    public static final int ACTION_WALKING_LEFT = 11;
    public static final int ACTION_WALKING_RIGHT = 12;
    public static final int ACTION_LEFT_SCREEN = 13;
    public static final int ACTION_RIGHT_SCREEN = 14;
    
    public static final int TRIGGER_POINT_SUSPICIOUS = 30;
    public static final int TRIGGER_POINT_FRENZY = 70;
    public static final int TRIGGER_POINT_DONE = 100;
    
    public float suspicion_gauge = 0;
}
