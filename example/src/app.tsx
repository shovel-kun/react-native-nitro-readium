import {Navigation} from './navigation';
import {PublicationProvider} from './contexts/PublicationContext';

export default function App() {
  return (
    <PublicationProvider>
      <Navigation />
    </PublicationProvider>
  );
}
