from PIL import Image
import numpy as np
from skimage import color
import torch
import torch.nn.functional as F
from IPython import embed


def load_img(img_path):
    out_np = np.asarray(Image.open(img_path))
    if (out_np.ndim == 2):
        out_np = np.tile(out_np[:, :, None], 3)
    print(f"load img shape: {out_np.shape}")
    return out_np


def resize_img(img, HW=(256, 256), resample=3):
    a = np.asarray(Image.fromarray(img).resize((HW[1], HW[0]), resample=resample))
    print(f"resize_img shape {a.shape}")
    return a


def preprocess_img(img_rgb_orig, HW=(256, 256), resample=3):
    # return original size L and resized L as torch Tensors
    print("preprocess_img:")
    img_rgb_rs = resize_img(img_rgb_orig, HW=HW, resample=resample)

    img_lab_orig = color.rgb2lab(img_rgb_orig)
    img_lab_rs = color.rgb2lab(img_rgb_rs)
    print(img_lab_orig.shape, img_lab_rs.shape)
    img_l_orig = img_lab_orig[:, :, 0]
    img_l_rs = img_lab_rs[:, :, 0]
    print(img_l_orig.shape, img_l_rs.shape)

    tens_orig_l = torch.Tensor(img_l_orig)[None, None, :, :]
    tens_rs_l = torch.Tensor(img_l_rs)[None, None, :, :]
    print("preprocess_img shape: ", tens_orig_l.shape, tens_rs_l.shape)
    return (tens_orig_l, tens_rs_l)


def postprocess_tens(tens_orig_l, out_ab, mode='bilinear'):
    # tens_orig_l 	1 x 1 x H_orig x W_orig
    # out_ab 		1 x 2 x H x W
    print(f"tens_orig_l shape: {tens_orig_l.shape}")
    HW_orig = tens_orig_l.shape[2:]
    print(HW_orig)
    HW = out_ab.shape[2:]
    print(HW)
    # call resize function if needed
    if (HW_orig[0] != HW[0] or HW_orig[1] != HW[1]):
        out_ab_orig = F.interpolate(out_ab, size=HW_orig, mode=mode)
    else:
        out_ab_orig = out_ab

    out_lab_orig = torch.cat((tens_orig_l, out_ab_orig), dim=1)
    print(f"postprocess_tens shape:{out_lab_orig.shape}")
    return color.lab2rgb(out_lab_orig.data.cpu().numpy()[0, ...].transpose((1, 2, 0)))
