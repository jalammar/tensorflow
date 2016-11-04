/*
 * Copyright 2016 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.demo;

import java.io.IOException;

import android.app.Fragment;
import org.tensorflow.demo.env.Logger;

public class ClassifierActivity extends CameraActivity {
  private static final Logger LOGGER = new Logger();

  // The settins for the MNIST model
  private static final int NUM_CLASSES = 10;
  private static final int INPUT_SIZE = 28; // MNIST images are 28 x 28 pixels
  private static final int IMAGE_MEAN = 117;
  private static final float IMAGE_STD = 1;
//  private static final String INPUT_NAME = "input:0";
//  private static final String OUTPUT_NAME = "softmax";
  private static final String INPUT_NAME = "input_node";
  private static final String OUTPUT_NAME = "output_node";


  //private static final String MODEL_FILE = "file:///android_asset/frozen_output_graph.pb";
  private static final String MODEL_FILE = "file:///android_asset/frozen_output_graph_17.pb";
  private static final String LABEL_FILE =
      "file:///android_asset/mnist_labels.txt";

  @Override
  protected Fragment createFragment() {
    final TensorFlowImageClassifier classifier = new TensorFlowImageClassifier();
    try {
      classifier.initializeTensorFlow(
        getAssets(), MODEL_FILE, LABEL_FILE, NUM_CLASSES, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD,
        INPUT_NAME, OUTPUT_NAME);
    } catch (final IOException e) {
      LOGGER.e(e, "Exception!");
    }

    return CameraConnectionFragment.newInstance(
        classifier, R.layout.camera_connection_fragment, INPUT_SIZE);
  }
}
