'use strict';

describe('Controller Tests', function() {

    describe('Patient Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPatient, MockAddress, MockContact, MockHeredoFamilyBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPatient = jasmine.createSpy('MockPatient');
            MockAddress = jasmine.createSpy('MockAddress');
            MockContact = jasmine.createSpy('MockContact');
            MockHeredoFamilyBkg = jasmine.createSpy('MockHeredoFamilyBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Patient': MockPatient,
                'Address': MockAddress,
                'Contact': MockContact,
                'HeredoFamilyBkg': MockHeredoFamilyBkg
            };
            createController = function() {
                $injector.get('$controller')("PatientDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:patientUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
