package com.black.healthbuddy.service;

import com.black.healthbuddy.service.IStepServiceCallback;

interface IStepService {
		boolean isRunning();
		void setSensitivity(int sens);
		void registerCallback(IStepServiceCallback cb);
		void unregisterCallback(IStepServiceCallback cb);
}