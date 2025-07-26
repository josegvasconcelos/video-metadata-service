-- Create videos table source index
CREATE INDEX idx_videos_source
    ON videos USING btree (source);
