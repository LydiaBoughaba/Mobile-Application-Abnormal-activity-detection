package com.boughaba.abnormaldetection.service;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;
import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class PredictionService {
    /*private static TensorFlowInferenceInterface inferenceInterface;
    private static final String MODEL_FILE = "model_dense.pb";
    private static final String INPUT_NODE = "input_node_input";
    private static final String[] OUTPUT_NODES = {"output_node/Softmax"};
    private static final String OUTPUT_NODE = "output_node/Softmax";
    private static final long[] INPUT_SIZE = {1, 150, 3};
    private static final int OUTPUT_SIZE = 2;


    public PredictionService(Context context){
        synchronized (PredictionService.class){
            if(inferenceInterface == null){
                inferenceInterface = new TensorFlowInferenceInterface(context.getAssets(), MODEL_FILE);
            }
        }
    }

    public float [] predictAnomaly(float[] data){
        float[] result = new float[OUTPUT_SIZE];
        inferenceInterface.feed(INPUT_NODE,data,INPUT_SIZE);
        inferenceInterface.run(OUTPUT_NODES);
        inferenceInterface.fetch(OUTPUT_NODE,result);
        //Normal vs Anomaly
        return result;
    }*/
    private static Interpreter interpreter;

    public PredictionService(Context context) {
        synchronized (PredictionService.class) {
            if (interpreter == null) {
                try {
                    AssetFileDescriptor assetFileDescriptor = context.getAssets().openFd("model_2_cnn_cfim_8_42.tflite");
                    FileInputStream fileInputStream = new FileInputStream(assetFileDescriptor.getFileDescriptor());
                    FileChannel fileChannel = fileInputStream.getChannel();
                    long declaredlength = assetFileDescriptor.getDeclaredLength();
                    long startoffset = assetFileDescriptor.getStartOffset();
                    ByteBuffer tflitefile = fileChannel.map(FileChannel.MapMode.READ_ONLY, startoffset, declaredlength);
                    interpreter = new Interpreter(tflitefile);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("erreur", e.getMessage() + "!!!");
                }
            }
        }
    }

    public float[][] predictAnomaly(float[][] data){
        float[][] result = new float[1][4];
        try {
            interpreter.run(data, result);
            return result;
        }catch(Exception e){
            Log.w("modelErreur",e.getMessage());
        }
        return null;
    }
}
