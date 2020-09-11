import os
import re
import sys
import shutil

assets_dir = sys.argv[1]
drawables_dir = 'app/src/main/res/'
for old_name in os.listdir(assets_dir):
    group = re.search(r'_[a-zA-Z0-9]+\.png$', old_name)
    if group:
        folder = group.group().replace("_", "").replace(".png", "")
        new_name = re.sub(r'_[a-zA-Z0-9]+\.png$', '.png', old_name)
        src = f"{assets_dir}{old_name}"
        dst_folder = f"{drawables_dir}mipmap-{folder}/"
        os.makedirs(dst_folder, exist_ok=True)
        dst = f"{dst_folder}/{new_name}"
        try:
            shutil.move(f"{assets_dir}{old_name}",
                        f"{drawables_dir}mipmap-{folder}/{new_name}")
            print(f"src: {assets_dir}{old_name} "
                  f"dst: {os.path.abspath(os.getcwd())}{drawables_dir}mipmap-{folder}/{new_name}")
        except FileNotFoundError as e:
            print("file not found")
