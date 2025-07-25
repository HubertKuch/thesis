package pl.hubertkuch.thesis.application.configuration;

import lombok.Data;

@Data
public abstract class HasLength {
    protected int maxLength;
    protected int minLength;
}
