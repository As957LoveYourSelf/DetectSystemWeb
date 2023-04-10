
import argparse
import matplotlib.pyplot as plt
from colorizers import *

if __name__ == '__main__':
	import sys
	import os
	curPath = os.path.abspath(os.path.dirname(__file__))
	rootPath = os.path.split(curPath)[0]
	sys.path.append(rootPath)

	parser = argparse.ArgumentParser()
	parser.add_argument('-i','--img_path', type=str, default='imgs/ansel_adams.jpg')
	parser.add_argument('--use_gpu', action='store_true', help='whether to use GPU')
	parser.add_argument('-o','--save_prefix', type=str, default='D:\IdeaObjects\DetectSystemWeb\src\main\saveimg\colorization\\test\\', help='will save into this file with {eccv16.png, siggraph17.png} suffixes')
	opt = parser.parse_args()

	# load colorizers
	# colorizer_eccv16 = eccv16(pretrained=True).eval()
	colorizer_siggraph17 = siggraph17(pretrained=True).eval()
	if(opt.use_gpu):
		# colorizer_eccv16.cuda()
		print("use gpu")
		colorizer_siggraph17.cuda()

	# default size to process images is 256x256
	# grab L channel in both original ("orig") and resized ("rs") resolutions
	img = load_img(opt.img_path)
	(tens_l_orig, tens_l_rs) = preprocess_img(img, HW=(256,256))
	if(opt.use_gpu):
		tens_l_rs = tens_l_rs.cuda()

	# colorizer outputs 256x256 ab map
	# resize and concatenate to original L channel
	img_bw = postprocess_tens(tens_l_orig, torch.cat((0*tens_l_orig,0*tens_l_orig),dim=1))
	# out_img_eccv16 = postprocess_tens(tens_l_orig, colorizer_eccv16(tens_l_rs).cpu())
	out_img_siggraph17 = postprocess_tens(tens_l_orig, colorizer_siggraph17(tens_l_rs).cpu())

	# plt.imsave('%s_eccv16.png'%opt.save_prefix, out_img_eccv16)
	plt.imsave('%s_siggraph17.png'%opt.save_prefix, out_img_siggraph17)


