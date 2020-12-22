package CoreEngine.Graphics;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
    public class Texture {
        private String filepath;
        private int texID;

        public ByteBuffer getBuffer() {
            return buffer;
        }

        private ByteBuffer buffer;
        private int width, height;

        public Texture(String filepath) {

        }
        public Texture(ByteBuffer buffer,int width, int height){
            this.width = width;
            this.height = height;
            this.buffer = buffer;
        }
        public Texture() {
            texID = -1;
            width = -1;
            height = -1;
        }

        public Texture(int width, int height) {
            this.filepath = "Generated";

            // Generate texture on GPU
            texID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texID);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                    0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        }

        public void init(String filepath) {
            this.filepath = filepath;

            // Generate texture on GPU
            texID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, texID);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            // When shrinking an image, pixelate
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            // Repeat image in both directions
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            // When stretching the image, pixelate
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            // When shrinking an image, pixelate
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);
            stbi_set_flip_vertically_on_load(true);
            ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

            if (image != null) {
                this.width = width.get(0);
                this.height = height.get(0);

                if (channels.get(0) == 3) {
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0),
                            0, GL_RGB, GL_UNSIGNED_BYTE, image);
                } else if (channels.get(0) == 4) {
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0),
                            0, GL_RGBA, GL_UNSIGNED_BYTE, image);
                } else {
                    assert false : "Error: (Texture) Unknown number of channesl '" + channels.get(0) + "'";
                }
            } else {
                assert false : "Error: (Texture) Could not load image '" + filepath + "'";
            }

            stbi_image_free(image);
        }

        public void bind() {
            glBindTexture(GL_TEXTURE_2D, texID);
        }

        public void unbind() {
            glBindTexture(GL_TEXTURE_2D, 0);
        }

        public int getWidth() {
            return this.width;
        }

        public String getFilepath() {
            return this.filepath;
        }

        public int getHeight() {
            return this.height;
        }

        public int getId() {
            return texID;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            if (!(o instanceof Texture)) return false;
            Texture oTex = (Texture)o;
            return oTex.getWidth() == this.width && oTex.getHeight() == this.height &&
                    oTex.getId() == this.texID &&
                    oTex.getFilepath().equals(this.filepath);
        }
}
