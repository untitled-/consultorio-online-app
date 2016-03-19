'use strict';

describe('Controller Tests', function() {

    describe('PathologicBkg Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPathologicBkg, MockAddiction, MockAlergy, MockQuirurgicalProcedure, MockTrauma, MockDisease, MockPatient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPathologicBkg = jasmine.createSpy('MockPathologicBkg');
            MockAddiction = jasmine.createSpy('MockAddiction');
            MockAlergy = jasmine.createSpy('MockAlergy');
            MockQuirurgicalProcedure = jasmine.createSpy('MockQuirurgicalProcedure');
            MockTrauma = jasmine.createSpy('MockTrauma');
            MockDisease = jasmine.createSpy('MockDisease');
            MockPatient = jasmine.createSpy('MockPatient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PathologicBkg': MockPathologicBkg,
                'Addiction': MockAddiction,
                'Alergy': MockAlergy,
                'QuirurgicalProcedure': MockQuirurgicalProcedure,
                'Trauma': MockTrauma,
                'Disease': MockDisease,
                'Patient': MockPatient
            };
            createController = function() {
                $injector.get('$controller')("PathologicBkgDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:pathologicBkgUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
