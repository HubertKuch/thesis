package pl.hubertkuch.thesis.util.configuration;

import lombok.Data;

@Data
public abstract class HasLength {
    protected int maxLength;
    protected int minLength;
}
