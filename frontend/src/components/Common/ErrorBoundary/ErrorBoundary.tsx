import type { ComponentType, PropsWithChildren } from 'react';
import { Component } from 'react';

export interface FallbackProps {
  message: string;
}

interface ErrorBoundaryProps {
  fallback: ComponentType<FallbackProps>;
}

interface ErrorBoundaryState {
  error: Error | null;
}

class ErrorBoundary extends Component<PropsWithChildren<ErrorBoundaryProps>, ErrorBoundaryState> {
  state: ErrorBoundaryState = {
    error: null,
  };

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    return { error };
  }

  render() {
    const { fallback: FallbackComponent } = this.props;

    if (this.state.error) {
      return <FallbackComponent message={this.state.error.message} />;
    }

    return this.props.children;
  }
}

export default ErrorBoundary;
