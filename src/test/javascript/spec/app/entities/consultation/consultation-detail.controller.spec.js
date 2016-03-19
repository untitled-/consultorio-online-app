'use strict';

describe('Controller Tests', function() {

    describe('Consultation Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockConsultation, MockLabTest, MockTreatment, MockSymptom, MockPatient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockConsultation = jasmine.createSpy('MockConsultation');
            MockLabTest = jasmine.createSpy('MockLabTest');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockSymptom = jasmine.createSpy('MockSymptom');
            MockPatient = jasmine.createSpy('MockPatient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Consultation': MockConsultation,
                'LabTest': MockLabTest,
                'Treatment': MockTreatment,
                'Symptom': MockSymptom,
                'Patient': MockPatient
            };
            createController = function() {
                $injector.get('$controller')("ConsultationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:consultationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
