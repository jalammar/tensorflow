/* Copyright 2015 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

// These utility functions allow for the conversion of RGB data to YUV data.

#include "tensorflow/examples/android/jni/rgb2mnist_pixel.h"

#include "tensorflow/core/platform/types.h"

using namespace tensorflow;


static inline void WriteMnistPixel(const int x, const int y, const int width,
                            const int r8, const int g8, const int b8,
                            uint8* const pY) {
  // Using formulas from http://msdn.microsoft.com/en-us/library/ms893078
  *pY = ((66 * r8 + 129 * g8 + 25 * b8 + 128) >> 8) + 16;

  // Odd widths get rounded up so that UV blocks on the side don't get cut off.
  const int blocks_per_row = (width + 1) / 2;

  // 2 bytes per UV block
  const int offset = 2 * (((y / 2) * blocks_per_row + (x / 2)));

}

void ConvertARGB8888ToMNISTPIXEL(const uint32* const input, uint8* const output,
                               int width, int height) {
  uint8* pY = output;
  //uint8* pUV = output + (width * height);
  const uint32* in = input;

  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
      const uint32 rgb = *in++;
#ifdef __APPLE__
      const int nB = (rgb >> 8) & 0xFF;
      const int nG = (rgb >> 16) & 0xFF;
      const int nR = (rgb >> 24) & 0xFF;
#else
      const int nR = (rgb >> 16) & 0xFF;
      const int nG = (rgb >> 8) & 0xFF;
      const int nB = rgb & 0xFF;
#endif
      WriteMnistPixel(x, y, width, nR, nG, nB, pY++);
    }
  }
}