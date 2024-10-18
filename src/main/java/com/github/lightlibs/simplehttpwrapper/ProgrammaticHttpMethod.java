package com.github.lightlibs.simplehttpwrapper;

public class ProgrammaticHttpMethod {

    private final String methodId;
    private final boolean hasBody;

    public ProgrammaticHttpMethod(String methodId, boolean hasBody) {
        this.methodId = methodId;
        this.hasBody = hasBody;
    }

    public String getMethodId() {
        return methodId;
    }

    public boolean hasBody() {
        return hasBody;
    }

}
