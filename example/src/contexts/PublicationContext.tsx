import React, {createContext, useContext, useState} from 'react';
import {Publication} from 'react-native-nitro-readium';

type PublicationContextType = {
  currentPublication: Publication | null;
  setCurrentPublication: (pub: Publication | null) => void;
};

const PublicationContext = createContext<PublicationContextType | undefined>(
  undefined,
);

export const PublicationProvider: React.FC<{children: React.ReactNode}> = ({
  children,
}) => {
  const [currentPublication, setCurrentPublication] =
    useState<Publication | null>(null);

  return (
    <PublicationContext.Provider
      value={{currentPublication, setCurrentPublication}}>
      {children}
    </PublicationContext.Provider>
  );
};

export const usePublication = () => {
  const context = useContext(PublicationContext);
  if (!context) {
    throw new Error('usePublication must be used within a PublicationProvider');
  }
  return context;
};
